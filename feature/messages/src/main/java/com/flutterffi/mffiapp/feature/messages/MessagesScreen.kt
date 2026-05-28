package com.flutterffi.mffiapp.feature.messages

import androidx.compose.runtime.Composable
import com.flutterffi.mffiapp.feature.shared.MffiModuleScreen

@Composable
fun MessagesScreen(uiState: MessagesUiState) {
    MffiModuleScreen(
        title = uiState.title,
        summary = uiState.summary,
        cards = uiState.cards,
        isLoading = uiState.isLoading,
    )
}
