package com.flutterffi.mffiapp.core.designsystem.adaptive

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class MffiWindowAdaptiveInfo(
    val widthSizeClass: WindowWidthSizeClass,
    val heightSizeClass: WindowHeightSizeClass,
) {
    val navigationType: MffiNavigationType =
        when (widthSizeClass) {
            WindowWidthSizeClass.Compact -> MffiNavigationType.BottomBar
            else -> MffiNavigationType.NavigationRail
        }

    val contentType: MffiContentType =
        when (widthSizeClass) {
            WindowWidthSizeClass.Expanded -> MffiContentType.ListDetail
            else -> MffiContentType.SinglePane
        }
}

enum class MffiNavigationType {
    BottomBar,
    NavigationRail,
}

enum class MffiContentType {
    SinglePane,
    ListDetail,
}

fun WindowSizeClass.toMffiAdaptiveInfo(): MffiWindowAdaptiveInfo {
    return MffiWindowAdaptiveInfo(
        widthSizeClass = widthSizeClass,
        heightSizeClass = heightSizeClass,
    )
}

val LocalMffiWindowAdaptiveInfo = staticCompositionLocalOf {
    MffiWindowAdaptiveInfo(
        widthSizeClass = WindowWidthSizeClass.Compact,
        heightSizeClass = WindowHeightSizeClass.Medium,
    )
}
