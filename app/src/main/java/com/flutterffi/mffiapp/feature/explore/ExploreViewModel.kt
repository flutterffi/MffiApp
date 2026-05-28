package com.flutterffi.mffiapp.feature.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val repository: MffiRepository,
) : ViewModel() {
    val uiState: StateFlow<ExploreUiState> = repository.observeFeatureCards("explore")
        .map { cards ->
            ExploreUiState(
                cards = cards,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExploreUiState(),
        )

    init {
        viewModelScope.launch {
            repository.seedDefaults()
        }
    }
}
