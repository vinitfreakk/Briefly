package com.accidentaldeveloper.briefly.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.repository.NewsApiRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    data object Initial : SearchUiState
    data object Loading : SearchUiState
    data class Success(val newsList: List<Article>) : SearchUiState
    data class Error(val error: String) : SearchUiState
}

class SearchViewModel(private val newsApiRepository: NewsApiRepository) : ViewModel() {
    private var searchJob: Job? = null

    private val _searchState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    fun searchNews(query: String){
        searchJob?.cancel()
        if (query.isBlank()) {
            return
        }
        searchJob = viewModelScope.launch {
            _searchState.value = SearchUiState.Loading
            runCatching {
                newsApiRepository.getNewsOfSpecificType(query)
            }.onSuccess {
                _searchState.value = SearchUiState.Success(it.articles)
            }.onFailure {
                _searchState.value = SearchUiState.Error(it.message.toString())
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob = null
    }

}