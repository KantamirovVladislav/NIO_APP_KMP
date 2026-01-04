package nio.notebook.app.core.errorHandler.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import nio.notebook.app.core.errorHandler.handler.ErrorHandler
import nio.notebook.app.core.errorHandler.model.ErrorEntity
import nio.notebook.app.core.errorHandler.model.ErrorPriority
import nio.notebook.app.core.errorHandler.model.ErrorType
import org.koin.compose.koinInject


@Composable
fun ViewErrorHandler(
    errorHandler: ErrorHandler = koinInject(),
    position: ErrorViewPosition = ErrorViewPosition.BottomRight
) {
    var currentError by remember { mutableStateOf<ErrorEntity?>(null) }

    LaunchedEffect(errorHandler) {
        errorHandler.errorsFlow
            .filter { it.type == ErrorType.VIEW }
            .collect { error ->
                currentError = error
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        currentError?.let { error ->
            ErrorToast(
                message = error.message,
                position = position,
                onDismiss = {
                    currentError = null
                }
            )
        }
    }
}
