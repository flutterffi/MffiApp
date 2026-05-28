package com.flutterffi.mffiapp.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class MffiSpacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 20.dp,
    val extraLarge: Dp = 32.dp,
)

val LocalMffiSpacing = staticCompositionLocalOf { MffiSpacing() }
