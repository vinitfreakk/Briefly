package com.accidentaldeveloper.briefly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accidentaldeveloper.briefly.model.ApiErrorResponse
import com.accidentaldeveloper.briefly.model.NewsApiException
import com.accidentaldeveloper.briefly.model.TopHeadlinesResponse
import com.accidentaldeveloper.briefly.repository.NewsApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TopHeadLinesUiState(){
    data object Initial: TopHeadLinesUiState()
    data object Loading: TopHeadLinesUiState()
    data class Success(val topHeadlinesResponse: TopHeadlinesResponse): TopHeadLinesUiState()
    data class Error(val error: String): TopHeadLinesUiState()
}
class HomeScreenViewModel(private val newsApiRepository: NewsApiRepository) : ViewModel() {

    private val _topHeadLinesResponse = MutableStateFlow<TopHeadLinesUiState>(TopHeadLinesUiState.Initial)
    val topHeadlinesResponse: StateFlow<TopHeadLinesUiState> = _topHeadLinesResponse.asStateFlow()

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines() {
        viewModelScope.launch {
            _topHeadLinesResponse.value = TopHeadLinesUiState.Loading
             runCatching {
                newsApiRepository.getTopHeadLines()
            }.onSuccess {
                _topHeadLinesResponse.value = TopHeadLinesUiState.Success(it)
            }.onFailure {
                _topHeadLinesResponse.value = TopHeadLinesUiState.Error(error = it.toString())
                 println("error from viewmodel: ${ it.message }")
            }

        }
    }
}