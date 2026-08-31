package com.accidentaldeveloper.briefly.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accidentaldeveloper.briefly.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SettingsUiState {
    data object Initial : SettingsUiState
    data object Loading : SettingsUiState
    data class Success(val string: String) : SettingsUiState
    data class Error(val error: String) : SettingsUiState
}

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    private val _apiKey = MutableStateFlow<SettingsUiState>(SettingsUiState.Initial)
    val apiKey: StateFlow<SettingsUiState> = _apiKey.asStateFlow()

    init {
        getApiKey()
    }
    fun saveApiKey(key: String) {
        viewModelScope.launch {
            _apiKey.value = SettingsUiState.Loading
            if (key.isBlank()) {
                _apiKey.value = SettingsUiState.Error(error = "Key cannot be empty")
                return@launch
            }
            runCatching {
                repository.saveApiKey(key)
            }.onSuccess { _apiKey.value = SettingsUiState.Success(key) }
                .onFailure {
                    _apiKey.value =
                        SettingsUiState.Error(error = it.message ?: "Failed To save the api key")
                }
        }
    }

    private fun getApiKey() {
        viewModelScope.launch {
            _apiKey.value = SettingsUiState.Loading
            runCatching { repository.getApiKey()}.onSuccess {
             _apiKey.value = SettingsUiState.Success(it)
            }.onFailure {
             _apiKey.value = SettingsUiState.Error(it.message ?: "Failed to fetch the api key")
            }
        }
    }
}