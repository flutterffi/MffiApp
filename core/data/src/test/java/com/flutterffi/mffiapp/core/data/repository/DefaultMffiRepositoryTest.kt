package com.flutterffi.mffiapp.core.data.repository

import com.flutterffi.mffiapp.core.data.local.FeatureCardDao
import com.flutterffi.mffiapp.core.data.local.FeatureCardEntity
import com.flutterffi.mffiapp.core.data.remote.MffiRemoteDataSource
import com.flutterffi.mffiapp.core.data.remote.PreviewPhotoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultMffiRepositoryTest {
    @Test
    fun `observe feature cards maps entities to domain cards`() = runTest {
        val dao = FakeFeatureCardDao(
            initialEntities = listOf(
                FeatureCardEntity(
                    id = 7,
                    module = "home",
                    title = "Dashboard",
                    description = "Compose UI backed by Flow state.",
                ),
            ),
        )
        val repository = DefaultMffiRepository(dao, FakeRemoteDataSource())

        val cards = repository.observeFeatureCards("home").first()

        assertEquals(1, cards.size)
        assertEquals(7, cards.first().id)
        assertEquals("Dashboard", cards.first().title)
        assertEquals("Compose UI backed by Flow state.", cards.first().description)
        assertEquals("home", dao.observedModule)
    }

    @Test
    fun `seed defaults inserts default cards when database is empty`() = runTest {
        val dao = FakeFeatureCardDao()
        val repository = DefaultMffiRepository(dao, FakeRemoteDataSource())

        repository.seedDefaults()

        assertEquals(12, dao.insertedItems.size)
        assertTrue(dao.insertedItems.any { it.module == "home" })
        assertTrue(dao.insertedItems.any { it.module == "explore" })
        assertTrue(dao.insertedItems.any { it.module == "messages" })
        assertTrue(dao.insertedItems.any { it.module == "profile" })
    }

    @Test
    fun `seed defaults skips insert when database has data`() = runTest {
        val dao = FakeFeatureCardDao(initialCount = 1)
        val repository = DefaultMffiRepository(dao, FakeRemoteDataSource())

        repository.seedDefaults()

        assertEquals(emptyList(), dao.insertedItems)
    }

    @Test
    fun `refresh preview image returns thumbnail url`() = runTest {
        val repository = DefaultMffiRepository(
            featureCardDao = FakeFeatureCardDao(),
            remoteDataSource = FakeRemoteDataSource(thumbnailUrl = "https://example.com/thumb.png"),
        )

        val result = repository.refreshPreviewImage()

        assertEquals("https://example.com/thumb.png", result)
    }

    @Test
    fun `refresh preview image returns null when remote fails`() = runTest {
        val repository = DefaultMffiRepository(
            featureCardDao = FakeFeatureCardDao(),
            remoteDataSource = FakeRemoteDataSource(failure = IllegalStateException("Remote failed")),
        )

        val result = repository.refreshPreviewImage()

        assertEquals(null, result)
    }
}

private class FakeFeatureCardDao(
    initialEntities: List<FeatureCardEntity> = emptyList(),
    private val initialCount: Int = initialEntities.size,
) : FeatureCardDao {
    private val entities = MutableStateFlow(initialEntities)
    var observedModule: String? = null
        private set
    var insertedItems: List<FeatureCardEntity> = emptyList()
        private set

    override fun observeByModule(module: String): Flow<List<FeatureCardEntity>> {
        observedModule = module
        return entities
    }

    override suspend fun count(): Int {
        return initialCount
    }

    override suspend fun insertAll(items: List<FeatureCardEntity>) {
        insertedItems = items
        entities.value = items
    }
}

private class FakeRemoteDataSource(
    private val thumbnailUrl: String = "https://example.com/default-thumb.png",
    private val failure: Throwable? = null,
) : MffiRemoteDataSource {
    override suspend fun getPreviewPhoto(): PreviewPhotoDto {
        failure?.let { throw it }
        return PreviewPhotoDto(
            id = 1,
            title = "Preview",
            url = "https://example.com/image.png",
            thumbnailUrl = thumbnailUrl,
        )
    }
}
