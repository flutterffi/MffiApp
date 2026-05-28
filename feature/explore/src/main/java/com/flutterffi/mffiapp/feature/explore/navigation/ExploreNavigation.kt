package com.flutterffi.mffiapp.feature.explore.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.flutterffi.mffiapp.core.navigation.ExploreRoute
import com.flutterffi.mffiapp.feature.explore.ExploreScreen
import com.flutterffi.mffiapp.feature.explore.ExploreViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.exploreScreen() {
    composable<ExploreRoute> {
        val viewModel: ExploreViewModel = koinViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        ExploreScreen(uiState = uiState.value)
    }
}
