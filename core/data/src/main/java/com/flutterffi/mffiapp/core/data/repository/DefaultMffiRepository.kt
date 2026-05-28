package com.flutterffi.mffiapp.core.data.repository

import com.flutterffi.mffiapp.core.data.local.FeatureCardDao
import com.flutterffi.mffiapp.core.data.local.FeatureCardEntity
import com.flutterffi.mffiapp.core.data.remote.MffiRemoteDataSource
import com.flutterffi.mffiapp.core.domain.model.FeatureCard
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultMffiRepository(
    private val featureCardDao: FeatureCardDao,
    private val remoteDataSource: MffiRemoteDataSource,
) : MffiRepository {
    override fun observeFeatureCards(module: String): Flow<List<FeatureCard>> {
        return featureCardDao.observeByModule(module).map { entities ->
            entities.map { entity ->
                FeatureCard(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    imageUrl = entity.imageUrl,
                )
            }
        }
    }

    override suspend fun seedDefaults() {
        if (featureCardDao.count() > 0) return

        featureCardDao.insertAll(
            listOf(
                FeatureCardEntity(module = "home", title = "Dashboard", description = "Compose UI backed by Flow state."),
                FeatureCardEntity(module = "home", title = "Persistence", description = "Room stores feature cards locally."),
                FeatureCardEntity(module = "home", title = "Networking", description = "Ktor Client defines the API boundary."),
                FeatureCardEntity(module = "home", title = "Remote Preview", description = "Remote content is cached locally after refresh."),
                FeatureCardEntity(module = "explore", title = "Modules", description = "Feature packages stay independently owned."),
                FeatureCardEntity(module = "explore", title = "Resources", description = "Icons, colors, spacing, and theme are centralized."),
                FeatureCardEntity(module = "explore", title = "Images", description = "Coil renders remote images in Compose."),
                FeatureCardEntity(module = "messages", title = "Events", description = "Coroutines handle async refresh work."),
                FeatureCardEntity(module = "messages", title = "State", description = "ViewModels expose StateFlow to the UI."),
                FeatureCardEntity(module = "messages", title = "DI", description = "Koin wires app dependencies."),
                FeatureCardEntity(module = "profile", title = "Environment", description = "Android Studio JBR 21 is the default Java runtime."),
                FeatureCardEntity(module = "profile", title = "Settings", description = "Feature settings can evolve behind ViewModels."),
                FeatureCardEntity(module = "profile", title = "Release", description = "Each stable feature can be tagged independently."),
            ),
        )
    }

    override suspend fun refreshHomeDashboard(): String? {
        val photo = remoteDataSource.getPreviewPhoto()
        val updatedRows = featureCardDao.updateCardDetails(
            module = "home",
            title = "Remote Preview",
            description = photo.title,
            imageUrl = photo.thumbnailUrl,
        )
        if (updatedRows == 0) {
            featureCardDao.insertAll(
                listOf(
                    FeatureCardEntity(
                        module = "home",
                        title = "Remote Preview",
                        description = photo.title,
                        imageUrl = photo.thumbnailUrl,
                    ),
                ),
            )
        }
        return photo.thumbnailUrl
    }
}
