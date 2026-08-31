package com.accidentaldeveloper.briefly.ui.newsDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accidentaldeveloper.briefly.database.stableId
import com.accidentaldeveloper.briefly.database.toNewsEntity
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.repository.BookMarkRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val bookMarkRepository: BookMarkRepository,
) : ViewModel() {

    private var bookMarkStatusJob: Job? = null
    private val _isBookMarked = MutableStateFlow<Boolean>(false)
    val isBookMarked: StateFlow<Boolean> = _isBookMarked.asStateFlow()


    fun toggleNews(article: Article) {
        viewModelScope.launch {
            bookMarkRepository.toggleNews(article.toNewsEntity())
        }
    }

     fun observeBookMarkStatus(article: Article) {
        viewModelScope.launch {
            bookMarkRepository.isBookMarked(article.stableId()).collect {
                _isBookMarked.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        bookMarkStatusJob?.cancel()
    }
}