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
                    imageUrl = "https://example.com/dashboard.png",
                ),
            ),
        )
        val repository = DefaultMffiRepository(dao, FakeRemoteDataSource())

        val cards = repository.observeFeatureCards("home").first()

        assertEquals(1, cards.size)
        assertEquals(7, cards.first().id)
        assertEquals("Dashboard", cards.first().title)
        assertEquals("Compose UI backed by Flow state.", cards.first().description)
        assertEquals("https://example.com/dashboard.png", cards.first().imageUrl)
        assertEquals("home", dao.observedModule)
    }

    @Test
    fun `seed defaults inserts default cards when database is empty`() = runTest {
        val dao = FakeFeatureCardDao()
        val repository = DefaultMffiRepository(dao, FakeRemoteDataSource())

        repository.seedDefaults()

        assertEquals(13, dao.insertedItems.size)
        assertTrue(dao.insertedItems.any { it.module == "home" })
        assertTrue(dao.insertedItems.any { it.module == "explore" })
        assertTrue(dao.insertedItems.any { it.module == "messages" })
        assertTrue(dao.insertedItems.any { it.module == "profile" })
        assertTrue(dao.insertedItems.any { it.title == "Remote Preview" })
    }

    @Test
    fun `refresh home dashboard updates cached remote preview card`() = runTest {
        val dao = FakeFeatureCardDao(
            initialEntities = listOf(
                FeatureCardEntity(
                    id = 1,
                    module = "home",
                    title = "Remote Preview",
                    description = "Remote content is cached locally after refresh.",
                ),
            ),
        )
        val repository = DefaultMffiRepository(
            featureCardDao = dao,
            remoteDataSource = FakeRemoteDataSource(
                title = "Remote title",
                thumbnailUrl = "https://example.com/thumb.png",
            ),
        )

        val result = repository.refreshHomeDashboard()

        assertEquals("https://example.com/thumb.png", result)
        val card = repository.observeFeatureCards("home").first().first()
        assertEquals("Remote title", card.description)
        assertEquals("https://example.com/thumb.png", card.imageUrl)
    }

    @Test
    fun `refresh home dashboard inserts cached remote preview card when missing`() = runTest {
        val dao = FakeFeatureCardDao()
        val repository = DefaultMffiRepository(
            featureCardDao = dao,
            remoteDataSource = FakeRemoteDataSource(
                title = "Remote title",
                thumbnailUrl = "https://example.com/thumb.png",
            ),
        )

        val result = repository.refreshHomeDashboard()

        assertEquals("https://example.com/thumb.png", result)
        assertEquals(1, dao.insertedItems.size)
        assertEquals("Remote Preview", dao.insertedItems.first().title)
        assertEquals("Remote title", dao.insertedItems.first().description)
        assertEquals("https://example.com/thumb.png", dao.insertedItems.first().imageUrl)
    }

    @Test
    fun `seed defaults skips insert when database has data`() = runTest {
        val dao = FakeFeatureCardDao(initialCount = 1)
        val repository = DefaultMffiRepository(dao, FakeRemoteDataSource())

        repository.seedDefaults()

        assertEquals(emptyList(), dao.insertedItems)
    }

    @Test
    fun `refresh home dashboard throws when remote fails`() = runTest {
        val repository = DefaultMffiRepository(
            featureCardDao = FakeFeatureCardDao(),
            remoteDataSource = FakeRemoteDataSource(failure = IllegalStateException("Remote failed")),
        )

        val result = runCatching { repository.refreshHomeDashboard() }

        assertTrue(result.isFailure)
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
        entities.value = entities.value + items
    }

    override suspend fun updateCardDetails(
        module: String,
        title: String,
        description: String,
        imageUrl: String?,
    ): Int {
        var updatedRows = 0
        entities.value = entities.value.map { entity ->
            if (entity.module == module && entity.title == title) {
                updatedRows += 1
                entity.copy(description = description, imageUrl = imageUrl)
            } else {
                entity
            }
        }
        return updatedRows
    }
}

private class FakeRemoteDataSource(
    private val title: String = "Preview",
    private val thumbnailUrl: String = "https://example.com/default-thumb.png",
    private val failure: Throwable? = null,
) : MffiRemoteDataSource {
    override suspend fun getPreviewPhoto(): PreviewPhotoDto {
        failure?.let { throw it }
        return PreviewPhotoDto(
            id = 1,
            title = title,
            url = "https://example.com/image.png",
            thumbnailUrl = thumbnailUrl,
        )
    }
}
