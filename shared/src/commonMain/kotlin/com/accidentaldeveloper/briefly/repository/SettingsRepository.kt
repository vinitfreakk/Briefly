package com.accidentaldeveloper.briefly.repository

import com.accidentaldeveloper.briefly.datastore.DataStoreManager

interface SettingsRepository {
    suspend fun getApiKey(): String

    suspend fun saveApiKey(key: String)
}

class SettingsRepositoryImpl(private val dataStoreManager: DataStoreManager): SettingsRepository{

    override suspend fun getApiKey(): String {
        return dataStoreManager.getApiKey()
    }

    override suspend fun saveApiKey(key: String) {
        dataStoreManager.saveApiKey(key)
    }

}