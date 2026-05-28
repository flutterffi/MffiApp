package com.flutterffi.mffiapp.core.data.remote

interface MffiRemoteDataSource {
    suspend fun getPreviewPhoto(): PreviewPhotoDto
}
