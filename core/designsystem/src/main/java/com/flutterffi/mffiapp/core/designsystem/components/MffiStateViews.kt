package com.flutterffi.mffiapp.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flutterffi.mffiapp.core.designsystem.theme.LocalMffiSpacing

@Composable
fun MffiLoadingState(
    modifier: Modifier = Modifier,
) {
    val spacing = LocalMffiSpacing.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.extraLarge),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MffiErrorState(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val spacing = LocalMffiSpacing.current

    MffiSurfaceCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
        ),
    ) {
        Column(
            modifier = Modifier.padding(spacing.large),
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            if (actionLabel != null && onAction != null) {
                Button(onClick = onAction) {
                    Text(text = actionLabel)
                }
            }
        }
    }
}

@Composable
fun MffiEmptyState(
    message: String,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalMffiSpacing.current

    MffiSurfaceCard(modifier = modifier.fillMaxWidth()) {
        Text(
            text = message,
            modifier = Modifier.padding(spacing.large),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
