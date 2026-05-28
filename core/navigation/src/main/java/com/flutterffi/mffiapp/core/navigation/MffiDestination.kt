package com.flutterffi.mffiapp.core.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.flutterffi.mffiapp.core.designsystem.icons.MffiIcons
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
data object HomeRoute

@Serializable
data object ExploreRoute

@Serializable
data object MessagesRoute

@Serializable
data object ProfileRoute

sealed class MffiDestination<T : Any>(
    val route: T,
    val routeClass: KClass<T>,
    val label: String,
    @param:DrawableRes val icon: Int,
) {
    data object Home : MffiDestination<HomeRoute>(HomeRoute, HomeRoute::class, "Home", MffiIcons.Home)
    data object Explore : MffiDestination<ExploreRoute>(ExploreRoute, ExploreRoute::class, "Explore", MffiIcons.Explore)
    data object Messages : MffiDestination<MessagesRoute>(MessagesRoute, MessagesRoute::class, "Messages", MffiIcons.Messages)
    data object Profile : MffiDestination<ProfileRoute>(ProfileRoute, ProfileRoute::class, "Profile", MffiIcons.Profile)
}

val MffiBottomDestinations = listOf(
    MffiDestination.Home,
    MffiDestination.Explore,
    MffiDestination.Messages,
    MffiDestination.Profile,
)

fun NavDestination?.isTopLevelDestination(destination: MffiDestination<*>): Boolean {
    return this?.hierarchy?.any { it.hasRoute(destination.routeClass) } == true
}
