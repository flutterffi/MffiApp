package com.flutterffi.mffiapp.feature.shared.state

import com.flutterffi.mffiapp.core.domain.model.FeatureCard

data class FeatureModuleUiState(
    val title: String,
    val summary: String,
    val cards: List<FeatureCard> = emptyList(),
    val previewImageUrl: String? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)
