package com.flutterffi.mffiapp.feature.home

import androidx.compose.runtime.Composable
import com.flutterffi.mffiapp.feature.shared.MffiModuleScreen

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRefresh: () -> Unit,
) {
    MffiModuleScreen(
        title = uiState.title,
        summary = uiState.summary,
        cards = uiState.cards,
        previewImageUrl = uiState.previewImageUrl,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        errorMessage = uiState.errorMessage,
        onRetry = onRefresh,
    )
}
