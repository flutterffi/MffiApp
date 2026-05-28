package com.flutterffi.mffiapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flutterffi.mffiapp.core.navigation.ExploreRoute
import com.flutterffi.mffiapp.core.navigation.HomeRoute
import com.flutterffi.mffiapp.core.navigation.MessagesRoute
import com.flutterffi.mffiapp.core.navigation.MffiBottomDestinations
import com.flutterffi.mffiapp.core.navigation.ProfileRoute
import com.flutterffi.mffiapp.core.navigation.isTopLevelDestination
import com.flutterffi.mffiapp.feature.explore.ExploreScreen
import com.flutterffi.mffiapp.feature.explore.ExploreViewModel
import com.flutterffi.mffiapp.feature.home.HomeScreen
import com.flutterffi.mffiapp.feature.home.HomeViewModel
import com.flutterffi.mffiapp.feature.messages.MessagesScreen
import com.flutterffi.mffiapp.feature.messages.MessagesViewModel
import com.flutterffi.mffiapp.feature.profile.ProfileScreen
import com.flutterffi.mffiapp.feature.profile.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MffiApp() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                MffiBottomDestinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination.isTopLevelDestination(destination),
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.label,
                            )
                        },
                        label = { Text(destination.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<HomeRoute> {
                val viewModel: HomeViewModel = koinViewModel()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                HomeScreen(uiState = uiState.value)
            }
            composable<ExploreRoute> {
                val viewModel: ExploreViewModel = koinViewModel()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                ExploreScreen(uiState = uiState.value)
            }
            composable<MessagesRoute> {
                val viewModel: MessagesViewModel = koinViewModel()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                MessagesScreen(uiState = uiState.value)
            }
            composable<ProfileRoute> {
                val viewModel: ProfileViewModel = koinViewModel()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                ProfileScreen(uiState = uiState.value)
            }
        }
    }
}
