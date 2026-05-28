package com.flutterffi.mffiapp.feature.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MessagesViewModel(
    private val repository: MffiRepository,
) : ViewModel() {
    val uiState: StateFlow<MessagesUiState> = repository.observeFeatureCards("messages")
        .map { cards ->
            MessagesUiState(
                cards = cards,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MessagesUiState(),
        )

    init {
        viewModelScope.launch {
            repository.seedDefaults()
        }
    }
}
