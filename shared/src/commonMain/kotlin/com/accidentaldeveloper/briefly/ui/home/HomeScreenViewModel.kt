package com.accidentaldeveloper.briefly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.repository.NewsApiRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TopHeadLinesUiState(){
    data object Initial: TopHeadLinesUiState()
    data object Loading: TopHeadLinesUiState()
    data class Success(val topHeadlinesResponse: List<Article>): TopHeadLinesUiState()
    data class Error(val error: String): TopHeadLinesUiState()
}
class HomeScreenViewModel(private val newsApiRepository: NewsApiRepository) : ViewModel() {

    private var fetchJob: Job? = null

    private val _topHeadLinesResponse = MutableStateFlow<TopHeadLinesUiState>(TopHeadLinesUiState.Initial)
    val topHeadlinesResponse: StateFlow<TopHeadLinesUiState> = _topHeadLinesResponse.asStateFlow()

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _topHeadLinesResponse.value = TopHeadLinesUiState.Loading
             runCatching {
                newsApiRepository.getTopHeadLines()
            }.onSuccess {
                _topHeadLinesResponse.value = TopHeadLinesUiState.Success(it.articles)
            }.onFailure {
                _topHeadLinesResponse.value = TopHeadLinesUiState.Error(error = it.message.toString())
                 println("error from viewmodel: ${ it.message }")
            }

        }
    }

    fun getNewsOfSpecificType(query: String){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _topHeadLinesResponse.value = TopHeadLinesUiState.Loading
            runCatching {
                newsApiRepository.getNewsOfSpecificType(query)
            }.onSuccess {
                _topHeadLinesResponse.value = TopHeadLinesUiState.Success(it.articles)
            }.onFailure {
                _topHeadLinesResponse.value = TopHeadLinesUiState.Error(error = it.message.toString())
                println("error from viewmodel: ${ it.message }")
            }
        }
    }

    fun getNewsTopics(): List<String>{
        return listOf(
            TRENDING_TOPIC,
            HEALTH_TOPIC,
            SPORTS_TOPIC,
            FINANCE_TOPIC,
            GAMES_TOPIC,
            BITCOIN_TOPIC,
            TECHNOLOGY_TOPIC,
            POLITICS_TOPIC
        )
    }

    companion object {
        const val TRENDING_TOPIC = "Trending"
        const val HEALTH_TOPIC = "Health"
        const val SPORTS_TOPIC = "Sports"
        const val FINANCE_TOPIC = "Finance"
        const val GAMES_TOPIC = "Games"
        const val BITCOIN_TOPIC = "Bitcoin"
        const val TECHNOLOGY_TOPIC = "Technology"
        const val POLITICS_TOPIC = "Politics"
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob = null
    }

}
