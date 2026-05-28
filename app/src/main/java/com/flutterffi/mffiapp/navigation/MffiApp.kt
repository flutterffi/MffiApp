package com.flutterffi.mffiapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flutterffi.mffiapp.core.navigation.HomeRoute
import com.flutterffi.mffiapp.core.navigation.MffiBottomDestinations
import com.flutterffi.mffiapp.core.navigation.isTopLevelDestination
import com.flutterffi.mffiapp.feature.explore.navigation.exploreScreen
import com.flutterffi.mffiapp.feature.home.navigation.homeScreen
import com.flutterffi.mffiapp.feature.messages.navigation.messagesScreen
import com.flutterffi.mffiapp.feature.profile.navigation.profileScreen

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
            homeScreen()
            exploreScreen()
            messagesScreen()
            profileScreen()
        }
    }
}
