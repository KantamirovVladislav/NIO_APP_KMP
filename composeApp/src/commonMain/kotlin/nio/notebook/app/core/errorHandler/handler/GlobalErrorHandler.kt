package nio.notebook.app.core.errorHandler.handler

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import nio.notebook.app.core.errorHandler.model.ErrorEntity
import nio.notebook.app.core.errorHandler.handler.ErrorHandler
import nio.notebook.app.core.errorHandler.repository.ErrorRepository

class GlobalErrorHandler(
    private val repo: ErrorRepository
) : ErrorHandler {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _errors = MutableSharedFlow<ErrorEntity>(
        extraBufferCapacity = 64
    )
    override val errorsFlow: SharedFlow<ErrorEntity> = _errors


    override suspend fun emit(error: ErrorEntity) {
        Napier.e(error.message, throwable = null, tag = error.source)


        scope.launch {
            try {
                repo.insert(error)
            } catch (t: Throwable) {
                Napier.e("Failed to persist error: ${t.message}")
            }
        }


        _errors.tryEmit(error)
    }
}