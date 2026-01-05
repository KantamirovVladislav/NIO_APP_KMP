package nio.notebook.app.core.errorHandler.dto

import nio.notebook.app.core.errorHandler.model.ErrorEntity
import nio.notebook.app.core.errorHandler.model.ErrorPriority
import nio.notebook.app.core.errorHandler.model.ErrorType


fun Throwable.toErrorEntity(
    type: ErrorType = ErrorType.SYSTEM,
    priority: ErrorPriority = ErrorPriority.MEDIUM,
    source: String = "unknown",
    messageOverride: String? = null,
    includeCauseSummary: Boolean = true
): ErrorEntity {
    val baseMsg = messageOverride
        ?: message
        ?: this::class.qualifiedName
        ?: "Unknown error"

    val finalMessage = if (includeCauseSummary && cause != null) {
        val c = cause!!
        val causeMsg = c.message ?: c::class.qualifiedName ?: "Unknown cause"
        "$baseMsg (cause: $causeMsg)"
    } else {
        baseMsg
    }

    return ErrorEntity(
        message = finalMessage,
        type = type,
        priority = priority,
        source = source,
        throwable = finalMessage
    )
}