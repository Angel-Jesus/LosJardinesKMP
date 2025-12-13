package com.pe.losjardines.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.pe.losjardines.cache.Database
import java.io.File

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val dbFilePath: String = getPath(isDebug = false)
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$dbFilePath")
        if(!File(dbFilePath).exists()) {
            Database.Schema.create(driver)
        }
        return driver
    }

    private fun getPath(isDebug: Boolean): String {
        val propertyKey = if(isDebug) "java.io.tmpdir" else "user.home"
        val parentFolderPath = System.getProperty(propertyKey) + "/SqlDelight"
        val parentFolder = File(parentFolderPath)
        if(!parentFolder.exists()) {
            parentFolder.mkdirs()
        }
        val databasePath = File(parentFolderPath, "$DB_NAME.db")
        return databasePath.absolutePath
    }

}