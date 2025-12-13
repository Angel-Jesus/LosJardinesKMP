package com.pe.losjardines.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.pe.losjardines.cache.Database

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "$DB_NAME.db")
    }
}