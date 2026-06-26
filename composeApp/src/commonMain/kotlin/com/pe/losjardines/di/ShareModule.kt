package com.pe.losjardines.di

import com.pe.losjardines.navigation.NavigationViewModel
import com.pe.losjardines.utils.NetworkChecker
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val splashScreenModules = module{
    singleOf(::NavigationViewModel)
}

expect val networkUtilsModule: Module

fun initKoinModularization(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(uiModules, dataModules, splashScreenModules, networkUtilsModule)
    }
}
