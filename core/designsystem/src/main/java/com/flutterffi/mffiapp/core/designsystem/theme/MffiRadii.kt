package com.flutterffi.mffiapp.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class MffiRadii(
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
)

val LocalMffiRadii = staticCompositionLocalOf { MffiRadii() }
