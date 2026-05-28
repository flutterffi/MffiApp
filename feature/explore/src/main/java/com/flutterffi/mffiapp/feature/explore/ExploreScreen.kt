package com.flutterffi.mffiapp.feature.explore

import androidx.compose.runtime.Composable
import com.flutterffi.mffiapp.feature.shared.MffiModuleScreen

@Composable
fun ExploreScreen(uiState: ExploreUiState) {
    MffiModuleScreen(
        title = uiState.title,
        summary = uiState.summary,
        cards = uiState.cards,
        isLoading = uiState.isLoading,
    )
}
