package com.pe.losjardines.di

import com.pe.losjardines.repository.AuthRepository
import com.pe.losjardines.repository.DatabaseRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.repository.auth.AuthRepositoryImpl
import com.pe.losjardines.repository.db.DatabaseRepositoryImpl
import com.pe.losjardines.repository.firestore.FirestoreRepositoryImpl
import org.koin.dsl.module

val dataModules = module {
    single<AuthRepository>{ AuthRepositoryImpl(get(), get()) }
    single<FirestoreRepository>{ FirestoreRepositoryImpl(get(), get()) }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }
}