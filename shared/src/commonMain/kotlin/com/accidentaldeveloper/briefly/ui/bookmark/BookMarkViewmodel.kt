package com.accidentaldeveloper.briefly.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accidentaldeveloper.briefly.database.NewsEntity
import com.accidentaldeveloper.briefly.database.toNewsArticle
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.platform.ShareManager
import com.accidentaldeveloper.briefly.platform.WebViewManager
import com.accidentaldeveloper.briefly.repository.BookMarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed interface BookMarkUiState {
    data object Initial : BookMarkUiState
    data object Loading : BookMarkUiState
    data class Success(val bookMarkedNews: List<Article>) : BookMarkUiState
    data class Error(val error: String) : BookMarkUiState
}

class BookMarkViewmodel(
    private val bookMarkRepository: BookMarkRepository,
    private val webViewManager: WebViewManager,
    private val shareManager: ShareManager
) : ViewModel() {
    private var _bookMarkedNews = MutableStateFlow<BookMarkUiState>(BookMarkUiState.Initial)
    val bookMarkedNews: StateFlow<BookMarkUiState> = _bookMarkedNews.asStateFlow()

    init {
        getAllBookMarkedNews()
    }

    fun getAllBookMarkedNews() {
        viewModelScope.launch {
            bookMarkRepository.getAllNews()
                .onStart { _bookMarkedNews.value = BookMarkUiState.Loading }
                .catch { e ->
                    _bookMarkedNews.value =
                        BookMarkUiState.Error(error = e.message ?: "Something Went Wrong")
                }
                .collect { newsList ->
                    _bookMarkedNews.value =
                        BookMarkUiState.Success(newsList.map { it.toNewsArticle() })
                }
        }
    }

    fun shareNews(article: Article) {
        val title = article.title ?: "Check this article"
        val url = article.url ?: return

        shareManager.share(
            "$title\n$url"
        )
    }

    fun openBrowser(article: Article){
        webViewManager.open(article.url?:"something went wrong")
    }
}