package com.pe.losjardines.di

import com.pe.losjardines.cache.Database
import com.pe.losjardines.db.DatabaseManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

const val DB_NAME = "sqldelight"

/**
 * Provee el SqlDriver específico de cada plataforma.
 * En Android resuelve el Context desde Koin (androidContext()) en lugar de un global.
 */
expect val platformDatabaseModule: Module

val databaseModules = module {
    includes(platformDatabaseModule)

    single { Database(get()) }
    singleOf(::DatabaseManager)
}
