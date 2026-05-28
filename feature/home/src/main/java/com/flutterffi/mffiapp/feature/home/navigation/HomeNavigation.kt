package com.flutterffi.mffiapp.feature.home.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.flutterffi.mffiapp.core.navigation.HomeRoute
import com.flutterffi.mffiapp.feature.home.HomeScreen
import com.flutterffi.mffiapp.feature.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        val viewModel: HomeViewModel = koinViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        HomeScreen(uiState = uiState.value)
    }
}
