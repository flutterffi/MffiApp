package com.flutterffi.mffiapp.core.navigation

import androidx.annotation.DrawableRes
import com.flutterffi.mffiapp.core.designsystem.icons.MffiIcons

sealed class MffiDestination(
    val route: String,
    val label: String,
    @param:DrawableRes val icon: Int,
) {
    data object Home : MffiDestination("home", "Home", MffiIcons.Home)
    data object Explore : MffiDestination("explore", "Explore", MffiIcons.Explore)
    data object Messages : MffiDestination("messages", "Messages", MffiIcons.Messages)
    data object Profile : MffiDestination("profile", "Profile", MffiIcons.Profile)
}

val MffiBottomDestinations = listOf(
    MffiDestination.Home,
    MffiDestination.Explore,
    MffiDestination.Messages,
    MffiDestination.Profile,
)
