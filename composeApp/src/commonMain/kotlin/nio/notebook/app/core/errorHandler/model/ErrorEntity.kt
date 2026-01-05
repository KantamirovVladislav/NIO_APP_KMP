package nio.notebook.app.core.errorHandler.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime


data class ErrorEntity @OptIn(ExperimentalTime::class) constructor(
    val id: Long = 0,
    val message: String,
    val type: ErrorType,
    val priority: ErrorPriority,
    val source: String,
    val throwable: String,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
)