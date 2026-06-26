package com.pe.losjardines.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.pe.losjardines.cache.Database
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformDatabaseModule: Module = module {
    single<SqlDriver> { createDesktopDriver() }
}

private fun createDesktopDriver(): SqlDriver {
    val dbFilePath = desktopDatabasePath(isDebug = false)
    val driver = JdbcSqliteDriver("jdbc:sqlite:$dbFilePath")
    if (!File(dbFilePath).exists()) {
        Database.Schema.create(driver)
    }
    return driver
}

private fun desktopDatabasePath(isDebug: Boolean): String {
    val propertyKey = if (isDebug) "java.io.tmpdir" else "user.home"
    val parentFolderPath = System.getProperty(propertyKey) + "/SqlDelight"
    val parentFolder = File(parentFolderPath)
    if (!parentFolder.exists()) {
        parentFolder.mkdirs()
    }
    return File(parentFolderPath, "$DB_NAME.db").absolutePath
}
