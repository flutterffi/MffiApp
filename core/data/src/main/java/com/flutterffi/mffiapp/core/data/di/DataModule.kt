package com.flutterffi.mffiapp.core.data.di

import androidx.room.Room
import com.flutterffi.mffiapp.core.data.local.MffiDatabase
import com.flutterffi.mffiapp.core.data.remote.KtorMffiRemoteDataSource
import com.flutterffi.mffiapp.core.data.remote.MffiRemoteDataSource
import com.flutterffi.mffiapp.core.data.repository.DefaultMffiRepository
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MffiDatabase::class.java,
            "mffi.db",
        ).build()
    }

    single { get<MffiDatabase>().featureCardDao() }

    single {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    single {
        HttpClient(Android) {
            defaultRequest {
                url {
                    takeFrom("https://jsonplaceholder.typicode.com/")
                    protocol = URLProtocol.HTTPS
                }
            }
            install(ContentNegotiation) {
                json(get())
            }
        }
    }

    single { KtorMffiRemoteDataSource(get()) } bind MffiRemoteDataSource::class
    single { DefaultMffiRepository(get(), get()) } bind MffiRepository::class
}
