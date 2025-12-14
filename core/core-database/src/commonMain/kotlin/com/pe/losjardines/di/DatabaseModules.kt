package com.pe.losjardines.di

import com.pe.losjardines.cache.Database
import com.pe.losjardines.db.DriverFactory
import org.koin.dsl.module

val databaseModules = module {
    single<Database> {
        Database(DriverFactory().createDriver())
    }
}