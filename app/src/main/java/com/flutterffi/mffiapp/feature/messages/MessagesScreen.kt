package com.flutterffi.mffiapp.feature.messages

import androidx.compose.runtime.Composable
import com.flutterffi.mffiapp.feature.shared.MffiModuleScreen

@Composable
fun MessagesScreen(uiState: MessagesUiState) {
    MffiModuleScreen(
        title = uiState.title,
        summary = uiState.summary,
        items = listOf("System updates", "Task notifications", "Runtime events"),
    )
}
