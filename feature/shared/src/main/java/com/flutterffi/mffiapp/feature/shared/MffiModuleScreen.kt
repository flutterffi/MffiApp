package com.flutterffi.mffiapp.feature.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.flutterffi.mffiapp.core.designsystem.images.MffiImages
import com.flutterffi.mffiapp.core.designsystem.strings.MffiStrings
import com.flutterffi.mffiapp.core.designsystem.theme.LocalMffiSpacing
import com.flutterffi.mffiapp.core.domain.model.FeatureCard

@Composable
fun MffiModuleScreen(
    title: String,
    summary: String,
    cards: List<FeatureCard>,
    previewImageUrl: String? = null,
    isLoading: Boolean,
    isRefreshing: Boolean = false,
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
) {
    val spacing = LocalMffiSpacing.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(spacing.large),
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
    ) {
        item(key = "header-$title") {
            Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = summary,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        if (previewImageUrl != null) {
            item(key = "preview-$previewImageUrl") {
                RemotePreviewCard(imageUrl = previewImageUrl)
            }
        }

        if (isLoading || isRefreshing) {
            item(key = "loading") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.extraLarge),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (errorMessage != null) {
            item(key = "error-$errorMessage") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(spacing.large),
                        verticalArrangement = Arrangement.spacedBy(spacing.medium),
                    ) {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                        if (onRetry != null) {
                            Button(onClick = onRetry) {
                                Text(text = stringResource(MffiStrings.RetryAction.id))
                            }
                        }
                    }
                }
            }
        }

        items(
            items = cards,
            key = { card -> card.id },
        ) { card ->
            FeatureCardRow(card = card)
        }
    }
}

@Composable
private fun RemotePreviewCard(imageUrl: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(MffiStrings.RemotePreviewContentDescription.id),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(MffiImages.Placeholder.id),
            error = painterResource(MffiImages.Placeholder.id),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
        )
    }
}

@Composable
private fun FeatureCardRow(card: FeatureCard) {
    val spacing = LocalMffiSpacing.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(spacing.large),
            verticalArrangement = Arrangement.spacedBy(spacing.small),
        ) {
            Text(
                text = card.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = card.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            if (card.imageUrl != null) {
                AsyncImage(
                    model = card.imageUrl,
                    contentDescription = card.title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(MffiImages.Placeholder.id),
                    error = painterResource(MffiImages.Placeholder.id),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                )
            }
        }
    }
}
