package nio.notebook.app.core.errorHandler.repository

import nio.notebook.app.ErrorQueries
import nio.notebook.app.Errors
import nio.notebook.app.core.errorHandler.model.ErrorEntity
import nio.notebook.app.core.errorHandler.model.ErrorPriority
import nio.notebook.app.core.errorHandler.model.ErrorType

class ErrorRepositoryImpl(
    private val queries: ErrorQueries
) : ErrorRepository {
    override suspend fun insert(error: ErrorEntity) {
// SQLDelight operations are synchronous by default, run on dispatcher if needed
        queries.insertError(
            Errors(
                message = error.message,
                type = error.type.name,
                priority = error.priority.name,
                source = error.source,
                throwable = error.throwable,
                timestamp = error.timestamp,
                id = error.id
            )

        )
    }


    override suspend fun getAll(): List<ErrorEntity> {
        return queries.selectAll().executeAsList().map {
            ErrorEntity(
                id = it.id,
                message = it.message,
                type = try { ErrorType.valueOf(it.type) } catch (t: Exception)
                { ErrorType.SYSTEM },
                priority = try { ErrorPriority.valueOf(it.priority) } catch (t: Exception)
                { ErrorPriority.MEDIUM },
                source = it.source,
                throwable = it.throwable.toString(),
                timestamp = it.timestamp
            )
        }
    }


    override suspend fun clear() {
        queries.clear()
    }
}