package nio.notebook.app.core.errorHandler.db

import nio.notebook.app.AppDatabase

class Database(factory: DatabaseDriverFactory) {
    private val driver = factory.createDriver()
    val db = AppDatabase(driver)

    val errors = db.errorQueries
}