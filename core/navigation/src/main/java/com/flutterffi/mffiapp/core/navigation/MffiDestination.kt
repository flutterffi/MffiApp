package com.flutterffi.mffiapp.core.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.flutterffi.mffiapp.core.designsystem.icons.MffiIcons
import com.flutterffi.mffiapp.core.designsystem.resources.MffiDrawableResource
import com.flutterffi.mffiapp.core.designsystem.resources.MffiStringResource
import com.flutterffi.mffiapp.core.designsystem.strings.MffiStrings
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
    val label: MffiStringResource,
    val icon: MffiDrawableResource,
) {
    data object Home : MffiDestination<HomeRoute>(HomeRoute, HomeRoute::class, MffiStrings.HomeTab, MffiIcons.Home)
    data object Explore : MffiDestination<ExploreRoute>(ExploreRoute, ExploreRoute::class, MffiStrings.ExploreTab, MffiIcons.Explore)
    data object Messages : MffiDestination<MessagesRoute>(MessagesRoute, MessagesRoute::class, MffiStrings.MessagesTab, MffiIcons.Messages)
    data object Profile : MffiDestination<ProfileRoute>(ProfileRoute, ProfileRoute::class, MffiStrings.ProfileTab, MffiIcons.Profile)
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
