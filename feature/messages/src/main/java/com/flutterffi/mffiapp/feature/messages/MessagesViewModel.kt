package com.flutterffi.mffiapp.feature.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.usecase.EnsureDefaultFeatureCardsUseCase
import com.flutterffi.mffiapp.core.domain.usecase.ObserveFeatureCardsUseCase
import com.flutterffi.mffiapp.core.model.MffiModule
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MessagesViewModel(
    observeFeatureCards: ObserveFeatureCardsUseCase,
    private val ensureDefaults: EnsureDefaultFeatureCardsUseCase,
) : ViewModel() {
    val uiState: StateFlow<MessagesUiState> = observeFeatureCards(MffiModule.Messages)
        .map { cards ->
            MessagesUiState(
                title = "Messages",
                summary = "Notifications, events, and async app feedback.",
                cards = cards,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MessagesUiState(
                title = "Messages",
                summary = "Notifications, events, and async app feedback.",
            ),
        )

    init {
        viewModelScope.launch {
            ensureDefaults()
        }
    }
}
