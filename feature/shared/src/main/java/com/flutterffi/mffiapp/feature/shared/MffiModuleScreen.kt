package com.flutterffi.mffiapp.feature.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.flutterffi.mffiapp.core.designsystem.adaptive.MffiAdaptiveContent
import com.flutterffi.mffiapp.core.designsystem.adaptive.MffiWindowAdaptiveInfo
import com.flutterffi.mffiapp.core.designsystem.components.MffiErrorState
import com.flutterffi.mffiapp.core.designsystem.components.MffiLoadingState
import com.flutterffi.mffiapp.core.designsystem.components.MffiRemoteImage
import com.flutterffi.mffiapp.core.designsystem.components.MffiSurfaceCard
import com.flutterffi.mffiapp.core.designsystem.preview.MffiPhoneDarkLargeFontPreview
import com.flutterffi.mffiapp.core.designsystem.preview.MffiPhoneLightPreview
import com.flutterffi.mffiapp.core.designsystem.preview.MffiTabletLightPreview
import com.flutterffi.mffiapp.core.designsystem.strings.MffiStrings
import com.flutterffi.mffiapp.core.designsystem.theme.LocalMffiSpacing
import com.flutterffi.mffiapp.core.designsystem.theme.MffiTheme
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

    MffiAdaptiveContent(modifier = Modifier.fillMaxSize()) {
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
                    MffiLoadingState()
                }
            }

            if (errorMessage != null) {
                item(key = "error-$errorMessage") {
                    MffiErrorState(
                        message = errorMessage,
                        actionLabel = if (onRetry != null) {
                            stringResource(MffiStrings.RetryAction.id)
                        } else {
                            null
                        },
                        onAction = onRetry,
                    )
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
}

@Composable
private fun RemotePreviewCard(imageUrl: String) {
    MffiSurfaceCard(modifier = Modifier.fillMaxWidth()) {
        MffiRemoteImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(MffiStrings.RemotePreviewContentDescription.id),
        )
    }
}

@Composable
private fun FeatureCardRow(card: FeatureCard) {
    val spacing = LocalMffiSpacing.current

    MffiSurfaceCard(modifier = Modifier.fillMaxWidth()) {
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
            card.imageUrl?.let { imageUrl ->
                MffiRemoteImage(
                    imageUrl = imageUrl,
                    contentDescription = card.title,
                )
            }
        }
    }
}

@MffiPhoneLightPreview
@Composable
private fun MffiModuleScreenPhonePreview() {
    MffiModuleScreenPreviewContent()
}

@MffiTabletLightPreview
@Composable
private fun MffiModuleScreenTabletPreview() {
    MffiModuleScreenPreviewContent(
        adaptiveInfo = MffiWindowAdaptiveInfo(
            widthSizeClass = WindowWidthSizeClass.Expanded,
            heightSizeClass = WindowHeightSizeClass.Medium,
        ),
    )
}

@MffiPhoneDarkLargeFontPreview
@Composable
private fun MffiModuleScreenErrorPreview() {
    MffiModuleScreenPreviewContent(
        darkTheme = true,
        errorMessage = "Preview data could not be refreshed.",
    )
}

@Composable
private fun MffiModuleScreenPreviewContent(
    darkTheme: Boolean = false,
    adaptiveInfo: MffiWindowAdaptiveInfo = MffiWindowAdaptiveInfo(
        widthSizeClass = WindowWidthSizeClass.Compact,
        heightSizeClass = WindowHeightSizeClass.Medium,
    ),
    errorMessage: String? = null,
) {
    MffiTheme(
        darkTheme = darkTheme,
        adaptiveInfo = adaptiveInfo,
    ) {
        MffiModuleScreen(
            title = "Home",
            summary = "Reusable feature screen layout for production modules.",
            cards = previewFeatureCards,
            isLoading = false,
            errorMessage = errorMessage,
            onRetry = {},
        )
    }
}

private val previewFeatureCards = listOf(
    FeatureCard(
        id = 1L,
        title = "Architecture",
        description = "MVVM feature modules with domain use cases and Flow-backed state.",
    ),
    FeatureCard(
        id = 2L,
        title = "Adaptive UI",
        description = "Shared layout rules for phone, tablet, dark mode, and large font previews.",
    ),
    FeatureCard(
        id = 3L,
        title = "Pure Kotlin Stack",
        description = "Compose, Ktor, Kotlinx Serialization, Room, Koin, and Coil.",
    ),
)
