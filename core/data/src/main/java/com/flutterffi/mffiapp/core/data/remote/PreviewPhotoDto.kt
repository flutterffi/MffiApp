package com.flutterffi.mffiapp.core.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PreviewPhotoDto(
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)
