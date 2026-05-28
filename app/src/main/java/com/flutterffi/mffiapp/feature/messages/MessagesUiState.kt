package com.flutterffi.mffiapp.feature.messages

import com.flutterffi.mffiapp.core.domain.model.FeatureCard

data class MessagesUiState(
    val title: String = "Messages",
    val summary: String = "Notifications, events, and async app feedback.",
    val cards: List<FeatureCard> = emptyList(),
    val isLoading: Boolean = true,
)
