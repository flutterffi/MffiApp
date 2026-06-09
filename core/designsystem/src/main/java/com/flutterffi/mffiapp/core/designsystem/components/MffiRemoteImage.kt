package com.flutterffi.mffiapp.core.designsystem.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.flutterffi.mffiapp.core.designsystem.images.MffiImages
import com.flutterffi.mffiapp.core.designsystem.theme.LocalMffiLayoutMetrics

@Composable
fun MffiRemoteImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val layoutMetrics = LocalMffiLayoutMetrics.current

    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = painterResource(MffiImages.Placeholder.id),
        error = painterResource(MffiImages.Placeholder.id),
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(layoutMetrics.previewImageAspectRatio),
    )
}
