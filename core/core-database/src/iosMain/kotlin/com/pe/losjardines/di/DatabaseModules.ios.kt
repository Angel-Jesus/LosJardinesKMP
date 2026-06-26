package com.pe.losjardines.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.pe.losjardines.cache.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(Database.Schema, "$DB_NAME.db")
    }
}
