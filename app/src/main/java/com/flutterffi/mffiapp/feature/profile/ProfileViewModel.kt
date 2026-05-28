package com.flutterffi.mffiapp.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: MffiRepository,
) : ViewModel() {
    val uiState: StateFlow<ProfileUiState> = repository.observeFeatureCards("profile")
        .map { cards ->
            ProfileUiState(
                cards = cards,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUiState(),
        )

    init {
        viewModelScope.launch {
            repository.seedDefaults()
        }
    }
}
