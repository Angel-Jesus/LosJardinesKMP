package com.pe.losjardines.di

import com.pe.losjardines.cache.Database
import com.pe.losjardines.db.DriverFactory
import com.pe.losjardines.firebase.auth.LoginService
import com.pe.losjardines.firebase.firestore.FirestoreService
import com.pe.losjardines.presentation.viewmodel.LoginViewModel
import com.pe.losjardines.repository.AuthRepository
import com.pe.losjardines.repository.FirestoreRepository
import com.pe.losjardines.repository.auth.AuthRepositoryImpl
import com.pe.losjardines.repository.firestore.FirestoreRepositoryImpl
import com.pe.losjardines.usecases.login.CheckSessionUseCase
import com.pe.losjardines.usecases.login.LoginUseCase
import com.pe.losjardines.usecases.login.LogoutUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val uiModules = module{
    viewModelOf(::LoginViewModel)
}

val domainModules = module {
    factoryOf(::CheckSessionUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::LogoutUseCase)
}

val dataModules = module {
    single<AuthRepository>{ AuthRepositoryImpl(get()) }
    single<FirestoreRepository>{ FirestoreRepositoryImpl(get()) }
}

val firebaseAuthModules = module {
    single<FirebaseAuth>{ Firebase.auth }
    single<FirebaseFirestore>{ Firebase.firestore }
    singleOf(::LoginService)
    singleOf(::FirestoreService)
}

val databaseModules = module {
    single<Database> {
        Database(DriverFactory().createDriver())
    }
}

fun initKoinModularization(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(uiModules, domainModules, dataModules, firebaseAuthModules, databaseModules)
    }
}
