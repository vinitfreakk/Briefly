package com.accidentaldeveloper.briefly.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    private val apiPreferenceKey = stringPreferencesKey("Briefly_Api_Key")

    private val apiKeyFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[apiPreferenceKey].orEmpty()
    }

    suspend fun saveApiKey(apiKey:String){
        dataStore.edit { preferences->
            preferences[apiPreferenceKey]=apiKey
        }
    }

    suspend fun getApiKey(): String = apiKeyFlow.first()
}