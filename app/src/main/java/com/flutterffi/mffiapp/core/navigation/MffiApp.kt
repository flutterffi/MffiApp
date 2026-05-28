package com.flutterffi.mffiapp.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flutterffi.mffiapp.feature.explore.ExploreScreen
import com.flutterffi.mffiapp.feature.explore.ExploreViewModel
import com.flutterffi.mffiapp.feature.home.HomeScreen
import com.flutterffi.mffiapp.feature.home.HomeViewModel
import com.flutterffi.mffiapp.feature.messages.MessagesScreen
import com.flutterffi.mffiapp.feature.messages.MessagesViewModel
import com.flutterffi.mffiapp.feature.profile.ProfileScreen
import com.flutterffi.mffiapp.feature.profile.ProfileViewModel

@Composable
fun MffiApp() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                MffiBottomDestinations.forEach { destination ->
                    val selected = currentDestination
                        ?.hierarchy
                        ?.any { it.route == destination.route } == true

                    NavigationBarItem(
                        selected = selected,
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
            startDestination = MffiDestination.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(MffiDestination.Home.route) {
                val viewModel: HomeViewModel = viewModel()
                HomeScreen(uiState = viewModel.uiState)
            }
            composable(MffiDestination.Explore.route) {
                val viewModel: ExploreViewModel = viewModel()
                ExploreScreen(uiState = viewModel.uiState)
            }
            composable(MffiDestination.Messages.route) {
                val viewModel: MessagesViewModel = viewModel()
                MessagesScreen(uiState = viewModel.uiState)
            }
            composable(MffiDestination.Profile.route) {
                val viewModel: ProfileViewModel = viewModel()
                ProfileScreen(uiState = viewModel.uiState)
            }
        }
    }
}
