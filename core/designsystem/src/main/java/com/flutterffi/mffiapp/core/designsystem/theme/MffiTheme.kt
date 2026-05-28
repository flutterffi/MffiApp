package com.flutterffi.mffiapp.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.flutterffi.mffiapp.core.designsystem.adaptive.LocalMffiWindowAdaptiveInfo
import com.flutterffi.mffiapp.core.designsystem.adaptive.MffiWindowAdaptiveInfo
import com.flutterffi.mffiapp.core.designsystem.colors.MffiColors

private val LightColorScheme = lightColorScheme(
    primary = MffiColors.Primary,
    onPrimary = MffiColors.OnPrimary,
    primaryContainer = MffiColors.PrimaryContainer,
    onPrimaryContainer = MffiColors.OnPrimaryContainer,
    secondary = MffiColors.Secondary,
    onSecondary = MffiColors.OnSecondary,
    secondaryContainer = MffiColors.SecondaryContainer,
    onSecondaryContainer = MffiColors.OnSecondaryContainer,
    background = MffiColors.Background,
    onBackground = MffiColors.OnBackground,
    surface = MffiColors.Surface,
    onSurface = MffiColors.OnSurface,
    surfaceVariant = MffiColors.SurfaceVariant,
    onSurfaceVariant = MffiColors.OnSurfaceVariant,
    outline = MffiColors.Outline,
)

private val DarkColorScheme = darkColorScheme(
    primary = MffiColors.PrimaryContainer,
    onPrimary = MffiColors.OnPrimaryContainer,
    primaryContainer = MffiColors.Primary,
    onPrimaryContainer = MffiColors.OnPrimary,
    secondary = MffiColors.SecondaryContainer,
    onSecondary = MffiColors.OnSecondaryContainer,
    background = MffiColors.OnBackground,
    onBackground = MffiColors.Background,
    surface = MffiColors.OnSurface,
    onSurface = MffiColors.Surface,
    surfaceVariant = MffiColors.OnSurfaceVariant,
    onSurfaceVariant = MffiColors.SurfaceVariant,
    outline = MffiColors.Outline,
)

@Composable
fun MffiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    adaptiveInfo: MffiWindowAdaptiveInfo = MffiWindowAdaptiveInfo(
        widthSizeClass = WindowWidthSizeClass.Compact,
        heightSizeClass = WindowHeightSizeClass.Medium,
    ),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalMffiSpacing provides MffiSpacing(),
        LocalMffiWindowAdaptiveInfo provides adaptiveInfo,
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = MffiTypography,
            content = content,
        )
    }
}
