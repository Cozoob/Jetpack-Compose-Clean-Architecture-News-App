package com.loc.newsapp.core.domain.use_case

import com.loc.newsapp.core.domain.ILocalDataRepository
import kotlinx.coroutines.flow.Flow

class ReadAppEntry (
    private val localDataRepository: ILocalDataRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return localDataRepository.readAppEntry()
    }
}