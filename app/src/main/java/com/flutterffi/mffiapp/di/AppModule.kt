package com.flutterffi.mffiapp.di

import com.flutterffi.mffiapp.startup.AppStartupCoordinator
import org.koin.dsl.module

val appModule = module {
    single { AppStartupCoordinator(get()) }
}
