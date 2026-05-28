package com.flutterffi.mffiapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.result.AppResult
import com.flutterffi.mffiapp.core.domain.usecase.ObserveFeatureCardsUseCase
import com.flutterffi.mffiapp.core.domain.usecase.RefreshPreviewImageUseCase
import com.flutterffi.mffiapp.core.model.MffiModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    observeFeatureCards: ObserveFeatureCardsUseCase,
    private val refreshPreviewImage: RefreshPreviewImageUseCase,
) : ViewModel() {
    private val previewImageUrl = MutableStateFlow<String?>(null)
    private val errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUiState> = combine(
        observeFeatureCards(MffiModule.Home),
        previewImageUrl,
        errorMessage,
    ) { cards, imageUrl, error ->
            HomeUiState(
                title = "Home",
                summary = "Application overview and quick actions.",
                cards = cards,
                previewImageUrl = imageUrl,
                isLoading = false,
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
        viewModelScope.launch {
            when (val result = refreshPreviewImage()) {
                is AppResult.Success -> previewImageUrl.value = result.data
                is AppResult.Error -> errorMessage.value = result.message
            }
        }
    }
}
