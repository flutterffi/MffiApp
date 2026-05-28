package com.flutterffi.mffiapp.feature.messages.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.flutterffi.mffiapp.core.navigation.MessagesRoute
import com.flutterffi.mffiapp.feature.messages.MessagesScreen
import com.flutterffi.mffiapp.feature.messages.MessagesViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.messagesScreen() {
    composable<MessagesRoute> {
        val viewModel: MessagesViewModel = koinViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        MessagesScreen(uiState = uiState.value)
    }
}
