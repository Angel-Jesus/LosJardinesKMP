package com.pe.losjardines.di

import com.pe.losjardines.navigation.NavigationViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val splashScreenModules = module{
    singleOf(::NavigationViewModel)
}
fun initKoinModularization(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(uiModules, splashScreenModules, domainModules, dataModules, firebaseModules, databaseModules)
    }
}
