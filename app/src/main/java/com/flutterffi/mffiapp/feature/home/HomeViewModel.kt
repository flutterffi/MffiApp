package com.flutterffi.mffiapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MffiRepository,
) : ViewModel() {
    private val previewImageUrl = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUiState> = combine(
        repository.observeFeatureCards("home"),
        previewImageUrl,
    ) { cards, imageUrl ->
            HomeUiState(
                cards = cards,
                previewImageUrl = imageUrl,
                isLoading = false,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(),
        )

    init {
        viewModelScope.launch {
            repository.seedDefaults()
            previewImageUrl.value = repository.refreshPreviewImage()
        }
    }
}
