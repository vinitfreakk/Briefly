package com.accidentaldeveloper.briefly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accidentaldeveloper.briefly.model.TopHeadlinesResponse
import com.accidentaldeveloper.briefly.repository.NewsApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.option.viewModelScopeFactory

class HomeScreenViewModel(private val newsApiRepository: NewsApiRepository) : ViewModel() {

    private val _topHeadLines = MutableStateFlow<TopHeadlinesResponse>(
        TopHeadlinesResponse(
            articles = emptyList(),
            status = "",
            totalResults = 0
        )
    )
    val topHeadlinesResponse: StateFlow<TopHeadlinesResponse> = _topHeadLines.asStateFlow()

    init {
        viewModelScope.launch {
            getTopHeadlines()
        }
    }

    suspend fun getTopHeadlines() {
        viewModelScope.launch {
            val response = newsApiRepository.getTopHeadLines()
            _topHeadLines.value = response
        }
    }
}