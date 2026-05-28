package com.flutterffi.mffiapp.feature.messages.di

import com.flutterffi.mffiapp.feature.messages.MessagesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val messagesModule = module {
    viewModelOf(::MessagesViewModel)
}
