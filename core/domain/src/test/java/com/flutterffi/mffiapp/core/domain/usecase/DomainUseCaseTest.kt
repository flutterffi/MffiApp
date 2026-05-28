package com.flutterffi.mffiapp.core.domain.usecase

import com.flutterffi.mffiapp.core.domain.model.FeatureCard
import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import com.flutterffi.mffiapp.core.domain.result.AppResult
import com.flutterffi.mffiapp.core.model.MffiModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame

class DomainUseCaseTest {
    @Test
    fun `observe feature cards uses module id`() = runTest {
        val repository = FakeMffiRepository()
        val useCase = ObserveFeatureCardsUseCase(repository)

        val result = useCase(MffiModule.Messages)

        assertSame(repository.cardsFlow, result)
        assertEquals("messages", repository.observedModule)
    }

    @Test
    fun `ensure default feature cards delegates to repository`() = runTest {
        val repository = FakeMffiRepository()
        val useCase = EnsureDefaultFeatureCardsUseCase(repository)

        useCase()

        assertEquals(1, repository.seedCalls)
    }

    @Test
    fun `refresh preview image returns success`() = runTest {
        val repository = FakeMffiRepository(previewImageUrl = "https://example.com/image.png")
        val useCase = RefreshPreviewImageUseCase(repository)

        val result = useCase()

        assertEquals(AppResult.Success("https://example.com/image.png"), result)
    }

    @Test
    fun `refresh preview image wraps repository error`() = runTest {
        val failure = IllegalStateException("Network unavailable")
        val repository = FakeMffiRepository(refreshFailure = failure)
        val useCase = RefreshPreviewImageUseCase(repository)

        val result = useCase()

        val error = assertIs<AppResult.Error>(result)
        assertEquals("Network unavailable", error.message)
        assertSame(failure, error.cause)
    }
}

private class FakeMffiRepository(
    private val previewImageUrl: String? = null,
    private val refreshFailure: Throwable? = null,
) : MffiRepository {
    val cardsFlow: Flow<List<FeatureCard>> = flowOf(emptyList())
    var observedModule: String? = null
        private set
    var seedCalls: Int = 0
        private set

    override fun observeFeatureCards(module: String): Flow<List<FeatureCard>> {
        observedModule = module
        return cardsFlow
    }

    override suspend fun seedDefaults() {
        seedCalls += 1
    }

    override suspend fun refreshPreviewImage(): String? {
        refreshFailure?.let { throw it }
        return previewImageUrl
    }
}
