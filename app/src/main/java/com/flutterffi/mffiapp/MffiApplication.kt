package com.flutterffi.mffiapp

import android.app.Application
import com.flutterffi.mffiapp.di.appModules
import com.flutterffi.mffiapp.startup.AppStartupCoordinator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MffiApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        val koinApplication = startKoin {
            androidLogger()
            androidContext(this@MffiApplication)
            modules(appModules)
        }
        koinApplication.koin.get<AppStartupCoordinator>().start(applicationScope)
    }

    override fun onTerminate() {
        applicationScope.cancel()
        super.onTerminate()
    }
}
