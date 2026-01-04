package nio.notebook.app.core.errorHandler.repository

import nio.notebook.app.core.errorHandler.model.ErrorEntity

interface ErrorRepository {
    suspend fun insert(error: ErrorEntity)
    suspend fun getAll(): List<ErrorEntity>
    suspend fun clear()
}