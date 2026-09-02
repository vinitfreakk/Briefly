package com.accidentaldeveloper.briefly.ui.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.accidentaldeveloper.briefly.Utils.getColorList
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.navigation.NewsDetailsNavArgs
import com.accidentaldeveloper.briefly.navigation.toNewsDetails
import com.accidentaldeveloper.briefly.ui.components.ErrorView
import com.accidentaldeveloper.briefly.ui.components.LoadingView
import com.accidentaldeveloper.briefly.ui.components.PagerItem

@Composable
fun BookMarkScreen(
    bookMarkViewmodel: BookMarkViewmodel,
    onNewsClicked: (newsDetails: NewsDetailsNavArgs) -> Unit
) {
    val bookMarkedNews by bookMarkViewmodel.bookMarkedNews.collectAsStateWithLifecycle()
    val colorList = getColorList()

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopBar(modifier = Modifier.statusBarsPadding().padding(16.dp))
        },
        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(24.dp))
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxSize()
        ) {
            when (bookMarkedNews) {
                BookMarkUiState.Initial -> Unit
                BookMarkUiState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxSize())
                }

                is BookMarkUiState.Success -> {
                    SuccessUi(
                        newsList = (bookMarkedNews as BookMarkUiState.Success).bookMarkedNews,
                        colorList = colorList,
                        onCardClicked = onNewsClicked,
                        openBrowser = {article ->
                            bookMarkViewmodel.openBrowser(article)
                        },
                        share = {article->
                            bookMarkViewmodel.shareNews(article)
                        }

                    )
                }

                is BookMarkUiState.Error -> {
                    ErrorView(
                        modifier = Modifier.fillMaxSize(),
                        message = (bookMarkedNews as BookMarkUiState.Error).error
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Saved News",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun SuccessUi(
    newsList: List<Article>,
    colorList: List<Color>,
    onCardClicked: (NewsDetailsNavArgs) -> Unit,
    share:(Article)-> Unit,
    openBrowser:(Article)-> Unit
) {
    if (newsList.isNotEmpty()) {
        val pagerState: PagerState = rememberPagerState(0, pageCount = { newsList.size })
        VerticalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(560.dp),
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 60.dp),
            pageSpacing = 12.dp
        ) { page ->
            val cyclicColor = colorList[page % colorList.size]
            PagerItem(
                modifier = Modifier.fillMaxSize(), // fill the slot left after contentPadding
                color = cyclicColor,
                headline = newsList[page].title ?: "N/A",
                content = newsList[page].description ?: "N/A",
                author = newsList[page].author ?: "Unknown",
                time = newsList[page].publishedAt ?: "",
                share = { share(newsList[page]) },
                openBrowser = { openBrowser((newsList[page]))},
                onCardClicked = {
                    onCardClicked(newsList[page].toNewsDetails(cyclicColor))
                }
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No Bookmarks Found", color = Color.White , textAlign = TextAlign.Center)
        }
    }

}