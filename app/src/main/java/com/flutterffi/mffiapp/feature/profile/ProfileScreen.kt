package com.flutterffi.mffiapp.feature.profile

import androidx.compose.runtime.Composable
import com.flutterffi.mffiapp.feature.shared.MffiModuleScreen

@Composable
fun ProfileScreen(uiState: ProfileUiState) {
    MffiModuleScreen(
        title = uiState.title,
        summary = uiState.summary,
        cards = uiState.cards,
        isLoading = uiState.isLoading,
    )
}
