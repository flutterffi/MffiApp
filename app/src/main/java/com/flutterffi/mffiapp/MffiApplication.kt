package com.flutterffi.mffiapp

import android.app.Application
import com.flutterffi.mffiapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MffiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MffiApplication)
            modules(appModules)
        }
    }
}
