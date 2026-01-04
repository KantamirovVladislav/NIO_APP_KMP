package nio.notebook.app.core.errorHandler.di

import nio.notebook.app.ErrorQueries
import nio.notebook.app.core.errorHandler.db.Database
import nio.notebook.app.core.errorHandler.db.DatabaseDriverFactory
import nio.notebook.app.core.errorHandler.handler.ErrorHandler
import nio.notebook.app.core.errorHandler.handler.GlobalErrorHandler
import nio.notebook.app.core.errorHandler.repository.ErrorRepository
import nio.notebook.app.core.errorHandler.repository.ErrorRepositoryImpl
import org.koin.dsl.module

val errorModule = module {

    single { Database(get()) }

    single<ErrorQueries> { get<Database>().errors }
    single<ErrorRepository> { ErrorRepositoryImpl(get()) }
    single<ErrorHandler> { GlobalErrorHandler(get()) }
    single<GlobalErrorHandler> { GlobalErrorHandler(get()) }
}