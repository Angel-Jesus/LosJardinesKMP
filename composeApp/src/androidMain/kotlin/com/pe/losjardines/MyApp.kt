package com.pe.losjardines

import android.app.Application
import com.pe.losjardines.db.initializer.AppContextWrapper
import com.pe.losjardines.di.initKoinModularization
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppContextWrapper.appContext = this
        initKoinModularization{
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
        }
    }
}