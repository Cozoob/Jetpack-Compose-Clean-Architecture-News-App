package com.loc.newsapp.core.domain.use_case

import com.loc.newsapp.core.domain.ILocalDataRepository

class WriteAppEntry(
    private val localDataRepository: ILocalDataRepository
) {
    suspend operator fun invoke() {
        localDataRepository.saveAppEntry()
    }
}