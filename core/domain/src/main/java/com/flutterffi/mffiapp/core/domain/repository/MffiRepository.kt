package com.flutterffi.mffiapp.core.domain.repository

import com.flutterffi.mffiapp.core.domain.model.FeatureCard
import kotlinx.coroutines.flow.Flow

interface MffiRepository {
    fun observeFeatureCards(module: String): Flow<List<FeatureCard>>
    suspend fun seedDefaults()
    suspend fun refreshPreviewImage(): String?
}
