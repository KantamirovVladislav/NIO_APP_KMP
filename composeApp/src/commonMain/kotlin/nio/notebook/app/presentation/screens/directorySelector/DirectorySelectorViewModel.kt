package nio.notebook.app.presentation.screens.directorySelector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.bookmarkData
import io.github.vinceglb.filekit.delete

import io.github.vinceglb.filekit.exists
import io.github.vinceglb.filekit.fromBookmarkData
import io.github.vinceglb.filekit.isDirectory
import io.github.vinceglb.filekit.list
import io.github.vinceglb.filekit.readString
import io.github.vinceglb.filekit.writeString
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nio.notebook.app.core.errorHandler.handler.ErrorHandler
import nio.notebook.app.data.storage.SettingsDataStore
import nio.notebook.app.domain.model.DirectoryAccessStatus
import nio.notebook.app.domain.model.DirectorySelectorUiState
import nio.notebook.app.domain.model.div
import kotlin.random.Random

/**
 * Name of the marker file used to identify the root directory of the notebook.
 */
private const val MARKER_FILE = ".nio_notebook_root"

/**
 * ViewModel for the directory selector screen.
 *
 * This ViewModel handles the logic for selecting, validating, and persisting the application's root directory.
 * It interacts with the file system and persists the chosen directory's reference for future sessions.
 *
 * @param errorHandler For handling and displaying errors that occur during file operations.
 * @param settingsDataStore For saving and retrieving the root directory bookmark.
 */
class DirectorySelectorViewModel(
    private val errorHandler: ErrorHandler,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val nextScreen = MutableStateFlow(false)
    val screen = nextScreen.asStateFlow()

    private val _uiState = MutableStateFlow(DirectorySelectorUiState())
    val uiState: StateFlow<DirectorySelectorUiState> = _uiState

    /**
     * Requests the UI to open the platform-specific directory picker.
     */
    fun openDialog() {
        _uiState.update { it.copy(showDialog = true) }
    }

    /**
     * Called by the UI after the user has selected a directory from the picker.
     *
     * @param directory The selected [PlatformFile], or null if the user canceled the operation.
     */
    fun onDirectoryPicked(directory: PlatformFile?) {
        _uiState.update { it.copy(showDialog = false, selected = directory) }
        if (directory != null) {
            checkSelectedDirectory()
        } else {
            _uiState.update { it.copy(status = DirectoryAccessStatus.Idle) }
        }
    }

    /**
     * Checks if the directory contains the marker file.
     *
     * @param dir The directory to check.
     * @return True if the marker file exists, false otherwise.
     */
    private suspend fun hasMarker(dir: PlatformFile): Boolean {
        val marker = dir / MARKER_FILE
        return runCatching { marker.exists() }.getOrDefault(false)
    }

    /**
     * Checks if the directory is empty.
     *
     * @param dir The directory to check.
     * @return True if the directory is empty, false otherwise.
     */
    private suspend fun isEmptyDir(dir: PlatformFile): Boolean {
        val items = runCatching { dir.list() }.getOrDefault(emptyList())
        return items.isEmpty()
    }


    /**
     * Initiates a check of the currently selected directory for read/write permissions and other criteria.
     * Updates the UI state with the result of the check.
     */
    fun checkSelectedDirectory() {
        val dir = _uiState.value.selected ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(status = DirectoryAccessStatus.Checking) }
            val status = runCatching { checkDirectoryAccess(dir) }
                .getOrElse { e ->
                    errorHandler.emit(e)
                    DirectoryAccessStatus.Error(e.message ?: "Failed to check directory")
                }
            _uiState.update { it.copy(status = status) }
        }
    }

    /**
     * Saves the selected directory as the root if it has passed all checks (status is OK).
     *
     * This function persists a bookmark of the directory, which is crucial for retaining access
     * across application restarts on platforms like Android and iOS. It also creates the marker file.
     */
    fun saveAsRootIfOk() {
        val dir = _uiState.value.selected ?: return
        if (_uiState.value.status != DirectoryAccessStatus.Ok) return

        viewModelScope.launch {
            runCatching {
                // Persist the bookmark for long-term access
                val bookmarkBytes = dir.bookmarkData().bytes
                settingsDataStore.saveRootDirectoryBookmark(bookmarkBytes)

                // Create the marker file to identify this directory in the future
                val marker = dir / MARKER_FILE
                runCatching {
                    marker.writeString("nio-root")
                    nextScreen.value = true
                }
            }.onFailure { e ->
                errorHandler.emit(e)
            }
        }
    }

    /**
     * Loads the root directory from storage on application start and checks its validity.
     * This allows the app to automatically re-open the last used notebook directory.
     */
    fun loadAndCheckRootDirectory() {
        viewModelScope.launch {
            runCatching {
                val bytes = settingsDataStore.getRootDirectoryBookmark() ?: return@runCatching null
                // Restore file access from the saved bookmark
                PlatformFile.fromBookmarkData(bytes)
            }.onSuccess { restored ->
                if (restored == null) return@onSuccess
                _uiState.update { it.copy(selected = restored) }
                checkSelectedDirectory()
            }.onFailure { e ->
                errorHandler.emit(e)
                _uiState.update { it.copy(status = DirectoryAccessStatus.Error("Failed to restore root directory")) }
            }
        }
    }

    /**
     * Cancels all coroutines launched in this ViewModel's scope.
     * Should be called when the ViewModel is no longer needed.
     */
    fun dispose() {
        viewModelScope.cancel()
    }

    // ---- CROSS-PLATFORM ACCESS CHECK ----

    /**
     * Performs a comprehensive, cross-platform check to ensure the directory is usable.
     *
     * The checks are:
     * 1. Directory must exist.
     * 2. The path must point to a directory, not a file.
     * 3. The application must have read permissions.
     * 4. The application must have write permissions (verified by creating and deleting a temp file).
     * 5. If the directory is being set for the first time (no marker file), it must be empty.
     *
     * @param dir The directory to check.
     * @return A [DirectoryAccessStatus] indicating the result of the check.
     */
    private suspend fun checkDirectoryAccess(dir: PlatformFile): DirectoryAccessStatus {
        if (!dir.exists()) return DirectoryAccessStatus.NotExists
        if (!dir.isDirectory()) return DirectoryAccessStatus.NotDirectory

        // Check for read access by trying to list its contents
        val canRead = runCatching { dir.list(); true }.getOrDefault(false)
        if (!canRead) return DirectoryAccessStatus.NotReadable

        // Check for write access by creating, writing, reading, and deleting a temporary file
        val testName = ".nio_access_test_${Random.nextInt(0, Int.MAX_VALUE)}.tmp"
        val testFile = dir / testName

        val canWrite = runCatching {
            testFile.writeString("ok")
            val contentRead = testFile.readString()
            testFile.delete()
            contentRead == "ok"
        }.getOrDefault(false)

        if (!canWrite) return DirectoryAccessStatus.NotWritable

        // If the directory is being used for the first time, it must be empty
        val markerExists = hasMarker(dir)
        if (!markerExists) {
            val empty = isEmptyDir(dir)
            if (!empty) return DirectoryAccessStatus.Error("The folder must be empty on first selection")
        }

        return DirectoryAccessStatus.Ok
    }
}