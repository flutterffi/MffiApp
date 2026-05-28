package com.flutterffi.mffiapp.di

import com.flutterffi.mffiapp.core.data.di.dataModule
import com.flutterffi.mffiapp.core.domain.di.domainModule
import com.flutterffi.mffiapp.feature.explore.di.exploreModule
import com.flutterffi.mffiapp.feature.home.di.homeModule
import com.flutterffi.mffiapp.feature.messages.di.messagesModule
import com.flutterffi.mffiapp.feature.profile.di.profileModule

val appModules = listOf(
    appModule,
    dataModule,
    domainModule,
    homeModule,
    exploreModule,
    messagesModule,
    profileModule,
)
