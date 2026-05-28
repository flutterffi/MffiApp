package com.flutterffi.mffiapp.startup

import com.flutterffi.mffiapp.core.domain.usecase.EnsureDefaultFeatureCardsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppStartupCoordinator(
    private val ensureDefaultFeatureCards: EnsureDefaultFeatureCardsUseCase,
) {
    fun start(scope: CoroutineScope): Job {
        return scope.launch {
            runCatching {
                ensureDefaultFeatureCards()
            }
        }
    }
}
