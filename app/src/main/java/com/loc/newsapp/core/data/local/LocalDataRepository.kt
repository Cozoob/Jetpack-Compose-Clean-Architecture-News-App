package com.loc.newsapp.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.loc.newsapp.core.domain.repository.ILocalDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val DATA_STORE_NAME = "userSettings"
const val KEY_APP_ENTRY = "appEntry"

class LocalDataRepository(private val context: Context) : ILocalDataRepository {
  override suspend fun saveAppEntry() {
    context.dataStore.edit { settings -> settings[PreferencesKeys.APP_ENTRY] = true }
  }

  override fun readAppEntry(): Flow<Boolean> {
    return context.dataStore.data.map { preferences ->
      preferences[PreferencesKeys.APP_ENTRY] ?: false
    }
  }
}

private val Context.dataStore: DataStore<Preferences> by
    preferencesDataStore(name = DATA_STORE_NAME)

private object PreferencesKeys {
  val APP_ENTRY = booleanPreferencesKey(name = KEY_APP_ENTRY)
}
