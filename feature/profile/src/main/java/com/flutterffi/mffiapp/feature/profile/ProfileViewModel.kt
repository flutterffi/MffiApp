package com.flutterffi.mffiapp.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.usecase.ObserveFeatureCardsUseCase
import com.flutterffi.mffiapp.core.model.MffiModule
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel(
    observeFeatureCards: ObserveFeatureCardsUseCase,
) : ViewModel() {
    val uiState: StateFlow<ProfileUiState> = observeFeatureCards(MffiModule.Profile)
        .map { cards ->
            ProfileUiState(
                title = "Profile",
                summary = "Account settings, preferences, and environment details.",
                cards = cards,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUiState(
                title = "Profile",
                summary = "Account settings, preferences, and environment details.",
            ),
        )
}
