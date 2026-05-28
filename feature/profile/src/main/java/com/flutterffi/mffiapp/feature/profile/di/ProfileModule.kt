package com.flutterffi.mffiapp.feature.profile.di

import com.flutterffi.mffiapp.feature.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)
}
