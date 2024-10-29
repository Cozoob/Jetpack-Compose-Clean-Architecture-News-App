package com.loc.newsapp.boarding.domain.use_case.app_entry

import com.loc.newsapp.core.domain.repository.ILocalDataRepository

class WriteAppEntry(
    private val localDataRepository: ILocalDataRepository
) {
    suspend operator fun invoke() {
        localDataRepository.saveAppEntry()
    }
}