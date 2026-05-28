package com.flutterffi.mffiapp.core.domain.model

data class FeatureCard(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
)
