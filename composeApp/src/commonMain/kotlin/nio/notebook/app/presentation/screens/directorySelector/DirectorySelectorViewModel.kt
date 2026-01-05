package nio.notebook.app.presentation.screens.directorySelector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.bookmarkData
import io.github.vinceglb.filekit.delete
import io.github.vinceglb.filekit.div
import io.github.vinceglb.filekit.exists
import io.github.vinceglb.filekit.fromBookmarkData
import io.github.vinceglb.filekit.isDirectory
import io.github.vinceglb.filekit.list
import io.github.vinceglb.filekit.readString
import io.github.vinceglb.filekit.writeString
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nio.notebook.app.core.errorHandler.handler.ErrorHandler
import nio.notebook.app.data.storage.SettingsDataStore
import nio.notebook.app.domain.model.DirectoryAccessStatus
import nio.notebook.app.domain.model.DirectorySelectorUiState
import kotlin.random.Random

private const val MARKER_FILE = ".nio_notebook_root"

class DirectorySelectorViewModel(
    private val errorHandler: ErrorHandler,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(DirectorySelectorUiState())
    val uiState: StateFlow<DirectorySelectorUiState> = _uiState

    /**
     * Открыть диалог (UI должен отреагировать и реально показать picker)
     */
    fun openDialog() {
        _uiState.update { it.copy(showDialog = true) }
    }

    /**
     * UI вызывает это после того, как picker вернул директорию (или null при отмене)
     */
    fun onDirectoryPicked(directory: PlatformFile?) {
        _uiState.update { it.copy(showDialog = false, selected = directory) }
        if (directory != null) {
            checkSelectedDirectory()
        } else {
            _uiState.update { it.copy(status = DirectoryAccessStatus.Idle) }
        }
    }

    private suspend fun hasMarker(dir: PlatformFile): Boolean {
        val marker = dir / MARKER_FILE
        return runCatching { marker.exists() }.getOrDefault(false)
    }

    private suspend fun isEmptyDir(dir: PlatformFile): Boolean {
        val items = runCatching { dir.list() }.getOrDefault(emptyList())
        return items.isEmpty()
    }


    /**
     * Проверка выбранной директории на чтение/запись
     */
    fun checkSelectedDirectory() {
        val dir = _uiState.value.selected ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(status = DirectoryAccessStatus.Checking) }
            val status = runCatching { checkDirectoryAccess(dir) }
                .getOrElse { e ->
                    errorHandler.emit(e)
                    DirectoryAccessStatus.Error(e.message ?: "Не удалось проверить директорию")
                }
            _uiState.update { it.copy(status = status) }
        }
    }

    /**
     * Сохранить как "root", но только если статус OK
     *
     * ВАЖНО: для Android/iOS лучше сохранять не path string, а bookmark bytes,
     * иначе после перезапуска можно потерять доступ к выбранной папке.
     * FileKit это решает через BookmarkData. :contentReference[oaicite:4]{index=4}
     */
    fun saveAsRootIfOk() {
        val dir = _uiState.value.selected ?: return
        if (_uiState.value.status != DirectoryAccessStatus.Ok) return

        viewModelScope.launch {
            runCatching {
                val bookmarkBytes = dir.bookmarkData().bytes
                settingsDataStore.saveRootDirectoryBookmark(bookmarkBytes)

                val marker = dir / MARKER_FILE
                runCatching { marker.writeString("nio-root") }
            }.onFailure { e ->
                errorHandler.emit(e)
            }
        }
    }

    /**
     * При старте экрана можно восстановить root директорию и проверить её
     */
    fun loadAndCheckRootDirectory() {
        viewModelScope.launch {
            runCatching {
                val bytes = settingsDataStore.getRootDirectoryBookmark() ?: return@runCatching null
                PlatformFile.fromBookmarkData(bytes)
            }.onSuccess { restored ->
                if (restored == null) return@onSuccess
                _uiState.update { it.copy(selected = restored) }
                checkSelectedDirectory()
            }.onFailure { e ->
                errorHandler.emit(e)
                _uiState.update { it.copy(status = DirectoryAccessStatus.Error("Не удалось восстановить root директорию")) }
            }
        }
    }

    fun dispose() {
        viewModelScope.cancel()
    }

    // ---- КРОССПЛАТФОРМЕННАЯ ПРОВЕРКА ДОСТУПА ----

    private suspend fun checkDirectoryAccess(dir: PlatformFile): DirectoryAccessStatus {
        if (!dir.exists()) return DirectoryAccessStatus.NotExists
        if (!dir.isDirectory()) return DirectoryAccessStatus.NotDirectory

        val canRead = runCatching { dir.list(); true }.getOrDefault(false)
        if (!canRead) return DirectoryAccessStatus.NotReadable

        val testName = ".nio_access_test_${Random.nextInt(0, Int.MAX_VALUE)}.tmp"
        val testFile = dir / testName

        val canWrite = runCatching {
            testFile.writeString("ok")
            val back = testFile.readString()
            testFile.delete()
            back == "ok"
        }.getOrDefault(false)

        if (!canWrite) return DirectoryAccessStatus.NotWritable

        val markerExists = hasMarker(dir)
        if (!markerExists) {
            val empty = isEmptyDir(dir)
            if (!empty) return DirectoryAccessStatus.Error("Папка должна быть пустой при первом выборе")
        }

        return DirectoryAccessStatus.Ok
    }
}