package com.flutterffi.mffiapp.feature.home

import androidx.compose.runtime.Composable
import com.flutterffi.mffiapp.feature.shared.MffiModuleScreen

@Composable
fun HomeScreen(uiState: HomeUiState) {
    MffiModuleScreen(
        title = uiState.title,
        summary = uiState.summary,
        items = listOf("Dashboard", "Quick entry", "Recent activity"),
    )
}
