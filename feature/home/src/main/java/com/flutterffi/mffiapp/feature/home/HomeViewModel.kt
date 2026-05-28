package com.flutterffi.mffiapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.result.AppResult
import com.flutterffi.mffiapp.core.domain.usecase.ObserveFeatureCardsUseCase
import com.flutterffi.mffiapp.core.domain.usecase.RefreshHomeDashboardUseCase
import com.flutterffi.mffiapp.core.model.MffiModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    observeFeatureCards: ObserveFeatureCardsUseCase,
    private val refreshHomeDashboard: RefreshHomeDashboardUseCase,
) : ViewModel() {
    private val previewImageUrl = MutableStateFlow<String?>(null)
    private val isRefreshing = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUiState> = combine(
        observeFeatureCards(MffiModule.Home),
        previewImageUrl,
        isRefreshing,
        errorMessage,
    ) { cards, imageUrl, refreshing, error ->
            HomeUiState(
                title = "Home",
                summary = "Application overview and quick actions.",
                cards = cards,
                previewImageUrl = imageUrl,
                isLoading = false,
                isRefreshing = refreshing,
                errorMessage = error,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(
                title = "Home",
                summary = "Application overview and quick actions.",
            ),
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            errorMessage.value = null
            when (val result = refreshHomeDashboard()) {
                is AppResult.Success -> {
                    previewImageUrl.value = result.data
                    errorMessage.value = null
                }
                is AppResult.Error -> errorMessage.value = result.message
            }
            isRefreshing.value = false
        }
    }
}
