package com.pe.losjardines.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pe.losjardines.cache.Database
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(Database.Schema, androidContext(), "$DB_NAME.db")
    }
}
