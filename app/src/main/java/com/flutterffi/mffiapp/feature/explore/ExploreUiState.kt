package com.flutterffi.mffiapp.feature.explore

import com.flutterffi.mffiapp.core.domain.model.FeatureCard

data class ExploreUiState(
    val title: String = "Explore",
    val summary: String = "Discover modules, services, and reusable components.",
    val cards: List<FeatureCard> = emptyList(),
    val isLoading: Boolean = true,
)
