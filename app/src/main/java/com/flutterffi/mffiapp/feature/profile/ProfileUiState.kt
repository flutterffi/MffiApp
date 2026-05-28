package com.flutterffi.mffiapp.feature.profile

import com.flutterffi.mffiapp.core.domain.model.FeatureCard

data class ProfileUiState(
    val title: String = "Profile",
    val summary: String = "Account settings, preferences, and environment details.",
    val cards: List<FeatureCard> = emptyList(),
    val isLoading: Boolean = true,
)
