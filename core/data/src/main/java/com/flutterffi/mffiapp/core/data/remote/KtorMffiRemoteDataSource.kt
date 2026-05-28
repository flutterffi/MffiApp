package com.flutterffi.mffiapp.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorMffiRemoteDataSource(
    private val httpClient: HttpClient,
) : MffiRemoteDataSource {
    override suspend fun getPreviewPhoto(): PreviewPhotoDto {
        return httpClient.get("photos/1").body()
    }
}
