package com.flutterffi.mffiapp.feature.explore.di

import com.flutterffi.mffiapp.feature.explore.ExploreViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val exploreModule = module {
    viewModelOf(::ExploreViewModel)
}
