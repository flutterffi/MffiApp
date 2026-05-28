package com.flutterffi.mffiapp.core.domain.usecase

import com.flutterffi.mffiapp.core.domain.model.FeatureCard
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import com.flutterffi.mffiapp.core.model.MffiModule
import kotlinx.coroutines.flow.Flow

class ObserveFeatureCardsUseCase(
    private val repository: MffiRepository,
) {
    operator fun invoke(module: MffiModule): Flow<List<FeatureCard>> {
        return repository.observeFeatureCards(module.id)
    }
}
