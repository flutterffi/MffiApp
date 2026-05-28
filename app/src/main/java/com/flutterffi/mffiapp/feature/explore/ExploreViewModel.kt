package com.flutterffi.mffiapp.feature.explore

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

class ExploreViewModel(
    observeFeatureCards: ObserveFeatureCardsUseCase,
    private val ensureDefaults: EnsureDefaultFeatureCardsUseCase,
) : ViewModel() {
    val uiState: StateFlow<ExploreUiState> = observeFeatureCards(MffiModule.Explore)
        .map { cards ->
            ExploreUiState(
                title = "Explore",
                summary = "Discover modules, services, and reusable components.",
                cards = cards,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExploreUiState(
                title = "Explore",
                summary = "Discover modules, services, and reusable components.",
            ),
        )

    init {
        viewModelScope.launch {
            ensureDefaults()
        }
    }
}
