package com.flutterffi.mffiapp.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flutterffi.mffiapp.core.designsystem.adaptive.LocalMffiWindowAdaptiveInfo
import com.flutterffi.mffiapp.core.designsystem.adaptive.MffiNavigationType
import com.flutterffi.mffiapp.core.navigation.HomeRoute
import com.flutterffi.mffiapp.core.navigation.MffiBottomDestinations
import com.flutterffi.mffiapp.core.navigation.MffiDestination
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
    val adaptiveInfo = LocalMffiWindowAdaptiveInfo.current
    val navigateToTopLevelDestination: (MffiDestination<*>) -> Unit = { destination ->
        navController.navigate(destination.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val appContent: @Composable (Modifier) -> Unit = { modifier ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = modifier,
        ) {
            homeScreen()
            exploreScreen()
            messagesScreen()
            profileScreen()
        }
    }

    when (adaptiveInfo.navigationType) {
        MffiNavigationType.BottomBar -> {
            Scaffold(
                bottomBar = {
                    MffiBottomNavigationBar(
                        currentDestination = currentDestination,
                        onDestinationSelected = navigateToTopLevelDestination,
                    )
                },
            ) { innerPadding ->
                appContent(Modifier.padding(innerPadding))
            }
        }

        MffiNavigationType.NavigationRail -> {
            Row {
                MffiNavigationRail(
                    currentDestination = currentDestination,
                    onDestinationSelected = navigateToTopLevelDestination,
                )
                Scaffold(modifier = Modifier.weight(1f)) { innerPadding ->
                    appContent(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun MffiBottomNavigationBar(
    currentDestination: NavDestination?,
    onDestinationSelected: (MffiDestination<*>) -> Unit,
) {
    NavigationBar {
        MffiBottomDestinations.forEach { destination ->
            val label = stringResource(destination.label.id)
            NavigationBarItem(
                selected = currentDestination.isTopLevelDestination(destination),
                onClick = { onDestinationSelected(destination) },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon.id),
                        contentDescription = label,
                    )
                },
                label = { Text(label) },
            )
        }
    }
}

@Composable
private fun MffiNavigationRail(
    currentDestination: NavDestination?,
    onDestinationSelected: (MffiDestination<*>) -> Unit,
) {
    NavigationRail {
        MffiBottomDestinations.forEach { destination ->
            val label = stringResource(destination.label.id)
            NavigationRailItem(
                selected = currentDestination.isTopLevelDestination(destination),
                onClick = { onDestinationSelected(destination) },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon.id),
                        contentDescription = label,
                    )
                },
                label = { Text(label) },
            )
        }
    }
}
