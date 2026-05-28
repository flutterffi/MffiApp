package com.flutterffi.mffiapp.feature.home.di

import com.flutterffi.mffiapp.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
}
