package nio.notebook.app.domain.model

import io.github.vinceglb.filekit.PlatformFile

sealed interface DirectoryAccessStatus {
    data object Idle : DirectoryAccessStatus
    data object Checking : DirectoryAccessStatus

    data object Ok : DirectoryAccessStatus
    data object NotExists : DirectoryAccessStatus
    data object NotDirectory : DirectoryAccessStatus
    data object NotReadable : DirectoryAccessStatus
    data object NotWritable : DirectoryAccessStatus

    data class Error(val debugMessage: String? = null) : DirectoryAccessStatus
}


data class DirectorySelectorUiState(
    val showDialog: Boolean = false,
    val selected: PlatformFile? = null,
    val status: DirectoryAccessStatus = DirectoryAccessStatus.Idle,
)