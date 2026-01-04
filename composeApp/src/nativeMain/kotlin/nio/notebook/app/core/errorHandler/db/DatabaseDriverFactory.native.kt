package nio.notebook.app.core.errorHandler.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import nio.notebook.app.AppDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(AppDatabase.Schema, "app.db")
}