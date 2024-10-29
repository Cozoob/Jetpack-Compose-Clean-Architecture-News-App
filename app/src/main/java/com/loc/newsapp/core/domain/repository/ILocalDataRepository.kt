package com.loc.newsapp.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ILocalDataRepository {
    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>
}