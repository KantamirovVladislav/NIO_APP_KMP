package nio.notebook.app.core.errorHandler.db

import org.koin.dsl.module

val platformModule = module {
    single<DatabaseDriverFactory> { DatabaseDriverFactory(get()) }
}