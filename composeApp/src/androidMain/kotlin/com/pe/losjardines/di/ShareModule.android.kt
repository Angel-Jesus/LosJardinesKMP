package com.pe.losjardines.di

import com.pe.losjardines.utils.NetworkChecker
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val networkUtilsModule: Module = module {
    single { NetworkChecker(context = androidContext()) }
}