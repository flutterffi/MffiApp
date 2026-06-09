package com.flutterffi.mffiapp.core.designsystem.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flutterffi.mffiapp.core.designsystem.theme.LocalMffiRadii

@Composable
fun MffiSurfaceCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface,
    ),
    content: @Composable ColumnScope.() -> Unit,
) {
    val radii = LocalMffiRadii.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(radii.medium),
        colors = colors,
        content = content,
    )
}
