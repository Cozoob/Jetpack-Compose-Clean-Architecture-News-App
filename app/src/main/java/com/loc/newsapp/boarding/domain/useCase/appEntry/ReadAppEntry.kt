package com.loc.newsapp.boarding.domain.useCase.appEntry

import com.loc.newsapp.core.domain.repository.ILocalDataRepository
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(private val localDataRepository: ILocalDataRepository) {
  operator fun invoke(): Flow<Boolean> {
    return localDataRepository.readAppEntry()
  }
}
