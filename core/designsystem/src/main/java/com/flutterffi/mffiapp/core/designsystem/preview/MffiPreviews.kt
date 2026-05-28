package com.flutterffi.mffiapp.core.designsystem.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone Light",
    group = "Mffi",
    widthDp = 393,
    heightDp = 852,
    showBackground = true,
)
annotation class MffiPhoneLightPreview

@Preview(
    name = "Tablet Light",
    group = "Mffi",
    widthDp = 840,
    heightDp = 1180,
    showBackground = true,
)
annotation class MffiTabletLightPreview

@Preview(
    name = "Phone Dark Large Font",
    group = "Mffi",
    widthDp = 393,
    heightDp = 852,
    fontScale = 1.3f,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
annotation class MffiPhoneDarkLargeFontPreview
