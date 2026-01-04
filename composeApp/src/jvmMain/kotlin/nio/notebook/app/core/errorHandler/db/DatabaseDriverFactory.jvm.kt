package nio.notebook.app.core.errorHandler.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import nio.notebook.app.AppDatabase
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val dbFile = File(System.getProperty("user.home"), ".nio-notebook/app.db")
        dbFile.parentFile.mkdirs()

        return JdbcSqliteDriver(
            url = "jdbc:sqlite:${dbFile.absolutePath}",
            schema = AppDatabase.Schema
        )
    }
}