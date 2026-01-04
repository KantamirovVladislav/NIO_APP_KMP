package nio.notebook.app.core.errorHandler.handler

import kotlinx.coroutines.flow.SharedFlow
import nio.notebook.app.core.errorHandler.model.ErrorEntity

interface ErrorHandler {
    suspend fun emit(error: ErrorEntity)
    val errorsFlow: SharedFlow<ErrorEntity>
}