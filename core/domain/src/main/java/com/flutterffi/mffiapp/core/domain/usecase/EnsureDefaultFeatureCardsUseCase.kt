package com.flutterffi.mffiapp.core.domain.usecase

import com.flutterffi.mffiapp.core.domain.repository.MffiRepository

class EnsureDefaultFeatureCardsUseCase(
    private val repository: MffiRepository,
) {
    suspend operator fun invoke() {
        repository.seedDefaults()
    }
}
