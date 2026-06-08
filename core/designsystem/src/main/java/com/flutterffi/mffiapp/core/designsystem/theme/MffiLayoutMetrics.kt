package com.flutterffi.mffiapp.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class MffiLayoutMetrics(
    val previewImageAspectRatio: Float = 16f / 9f,
)

val LocalMffiLayoutMetrics = staticCompositionLocalOf { MffiLayoutMetrics() }
