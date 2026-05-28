package com.flutterffi.mffiapp.feature.home

import com.flutterffi.mffiapp.core.domain.model.FeatureCard

data class HomeUiState(
    val title: String = "Home",
    val summary: String = "Application overview and quick actions.",
    val cards: List<FeatureCard> = emptyList(),
    val previewImageUrl: String? = null,
    val isLoading: Boolean = true,
)
