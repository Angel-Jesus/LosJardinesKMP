package com.pe.losjardines.db

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pe.losjardines.cache.Database
import com.pe.losjardines.db.initializer.AppContextWrapper

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, AppContextWrapper.appContext!!, "$DB_NAME.db")
    }
}