package com.flutterffi.mffiapp.core.domain.di

import com.flutterffi.mffiapp.core.domain.usecase.EnsureDefaultFeatureCardsUseCase
import com.flutterffi.mffiapp.core.domain.usecase.ObserveFeatureCardsUseCase
import com.flutterffi.mffiapp.core.domain.usecase.RefreshHomeDashboardUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { EnsureDefaultFeatureCardsUseCase(get()) }
    factory { ObserveFeatureCardsUseCase(get()) }
    factory { RefreshHomeDashboardUseCase(get()) }
}
