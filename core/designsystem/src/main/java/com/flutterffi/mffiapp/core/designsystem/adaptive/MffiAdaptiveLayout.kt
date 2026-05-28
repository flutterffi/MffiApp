package com.flutterffi.mffiapp.core.designsystem.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MffiAdaptiveContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val adaptiveInfo = LocalMffiWindowAdaptiveInfo.current
    val maxContentWidth = when (adaptiveInfo.contentType) {
        MffiContentType.SinglePane -> 720.dp
        MffiContentType.ListDetail -> 1120.dp
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(modifier = Modifier.widthIn(max = maxContentWidth)) {
            content()
        }
    }
}

@Composable
fun MffiAdaptivePreviewSurface(
    widthSizeClass: androidx.compose.material3.windowsizeclass.WindowWidthSizeClass,
    heightSizeClass: androidx.compose.material3.windowsizeclass.WindowHeightSizeClass,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalMffiWindowAdaptiveInfo provides MffiWindowAdaptiveInfo(
            widthSizeClass = widthSizeClass,
            heightSizeClass = heightSizeClass,
        ),
    ) {
        BoxWithConstraints {
            content()
        }
    }
}
