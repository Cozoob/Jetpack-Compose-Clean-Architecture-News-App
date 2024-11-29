package com.loc.newsapp.boarding.domain.useCase.appEntry

import com.loc.newsapp.core.domain.repository.ILocalDataRepository

class WriteAppEntry(private val localDataRepository: ILocalDataRepository) {
  suspend operator fun invoke() {
    localDataRepository.saveAppEntry()
  }
}
