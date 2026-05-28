package com.flutterffi.mffiapp.core.domain.usecase

import com.flutterffi.mffiapp.core.domain.repository.MffiRepository
import com.flutterffi.mffiapp.core.domain.result.AppResult
import com.flutterffi.mffiapp.core.domain.result.runAppCatching

class RefreshHomeDashboardUseCase(
    private val repository: MffiRepository,
) {
    suspend operator fun invoke(): AppResult<String?> {
        return runAppCatching {
            repository.refreshHomeDashboard()
        }
    }
}
