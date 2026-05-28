package com.flutterffi.mffiapp.feature.profile.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.flutterffi.mffiapp.core.navigation.ProfileRoute
import com.flutterffi.mffiapp.feature.profile.ProfileScreen
import com.flutterffi.mffiapp.feature.profile.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.profileScreen() {
    composable<ProfileRoute> {
        val viewModel: ProfileViewModel = koinViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        ProfileScreen(uiState = uiState.value)
    }
}
