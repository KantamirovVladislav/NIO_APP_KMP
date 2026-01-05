package nio.notebook.app.presentation.screens.directorySelector

import androidx.compose.runtime.Composable
import nio.notebook.app.domain.model.DirectoryAccessStatus
import org.jetbrains.compose.resources.stringResource
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.dir_status_idle
import nio_app.composeapp.generated.resources.dir_status_checking
import nio_app.composeapp.generated.resources.dir_status_ok
import nio_app.composeapp.generated.resources.dir_status_not_exists
import nio_app.composeapp.generated.resources.dir_status_not_directory
import nio_app.composeapp.generated.resources.dir_status_not_readable
import nio_app.composeapp.generated.resources.dir_status_not_writable
import nio_app.composeapp.generated.resources.dir_status_error

@Composable
fun statusText(status: DirectoryAccessStatus): String = when (status) {
    DirectoryAccessStatus.Idle -> stringResource(Res.string.dir_status_idle)
    DirectoryAccessStatus.Checking -> stringResource(Res.string.dir_status_checking)
    DirectoryAccessStatus.Ok -> stringResource(Res.string.dir_status_ok)
    DirectoryAccessStatus.NotExists -> stringResource(Res.string.dir_status_not_exists)
    DirectoryAccessStatus.NotDirectory -> stringResource(Res.string.dir_status_not_directory)
    DirectoryAccessStatus.NotReadable -> stringResource(Res.string.dir_status_not_readable)
    DirectoryAccessStatus.NotWritable -> stringResource(Res.string.dir_status_not_writable)
    is DirectoryAccessStatus.Error -> status.debugMessage
        ?: stringResource(Res.string.dir_status_error)
}