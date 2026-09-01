package com.accidentaldeveloper.briefly.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import briefly.shared.generated.resources.Res
import briefly.shared.generated.resources.ic_settings
import com.accidentaldeveloper.briefly.Utils.cleanArticleText
import com.accidentaldeveloper.briefly.Utils.getColorList
import com.accidentaldeveloper.briefly.Utils.toRelativeTime
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.navigation.NewsDetailsNavArgs
import com.accidentaldeveloper.briefly.navigation.toNewsDetails
import com.accidentaldeveloper.briefly.ui.components.ArticleActionRow
import com.accidentaldeveloper.briefly.ui.components.ErrorView
import com.accidentaldeveloper.briefly.ui.components.LoadingView
import com.accidentaldeveloper.briefly.ui.components.PagerItem
import com.accidentaldeveloper.briefly.ui.home.HomeScreenViewModel.Companion.TRENDING_TOPIC
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    onCardClicked: (NewsDetailsNavArgs) -> Unit,
    onSettingsClicked: () -> Unit
) {
    val response by homeScreenViewModel.topHeadlinesResponse.collectAsStateWithLifecycle()
    val newsList = homeScreenViewModel.getNewsTopics()
    val colorList = getColorList()
    var selectedTopic by remember { mutableStateOf("Trending") }
    Scaffold(topBar = {
        TopBar(modifier = Modifier.statusBarsPadding().padding(16.dp), onSettingsClicked = onSettingsClicked)
    }, containerColor = Color.Black) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color.Black)) {
            Column(modifier = Modifier.padding(16.dp).fillMaxSize().background(Color.Black)) {
                NewsTopics(
                    newsList,
                    selectedTopic,
                    onSelectedTopic = {
                        selectedTopic = it
                        if (it == TRENDING_TOPIC) {
                            homeScreenViewModel.getTopHeadlines()
                        } else {
                            homeScreenViewModel.getNewsOfSpecificType(it)
                        }

                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                when (response) {
                    is TopHeadLinesUiState.Error -> { ErrorView(
                            modifier = Modifier.fillMaxSize(),
                            message = (response as TopHeadLinesUiState.Error).error
                        ) }
                    TopHeadLinesUiState.Initial -> Unit
                    TopHeadLinesUiState.Loading -> { LoadingView(modifier = Modifier.fillMaxSize()) }
                    is TopHeadLinesUiState.Success -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            SuccessUi(
                                articleList = (response as TopHeadLinesUiState.Success).topHeadlinesResponse,
                                colorList = colorList,
                                onCardClicked = onCardClicked

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsTopics(
    newsList: List<String>,
    selectedTopic: String,
    onSelectedTopic: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(48.dp).background(Color.Transparent)
    ) {
        items(newsList.size) { index ->
            val topic = newsList[index]
            val isSelected = if (topic == selectedTopic) true else false
            Text(
                text = topic,
                fontSize = if (isSelected) 28.sp else 18.sp,
                fontWeight = FontWeight.Normal,
                color = if (isSelected) Color.White else Color.DarkGray,
                modifier = Modifier.clickable(onClick = {
                    onSelectedTopic(topic)
                })
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NewsPager(
    newsList: List<Article>,
    colorList: List<Color>,
    onCardClicked: (NewsDetailsNavArgs) -> Unit
) {
    val pagerState: PagerState = rememberPagerState(initialPage = 0, pageCount = { newsList.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        pageSpacing = 12.dp
    ) { page ->
        val cyclicColor = colorList[page % colorList.size]
        PagerItem(
            modifier = Modifier.fillMaxHeight(0.8f),
            color = cyclicColor,
            headline = newsList[page].title ?: "N/A",
            content = newsList[page].description ?: "N/A",
            author = newsList[page].author ?: "Unknown",
            time = newsList[page].publishedAt ?: "",
            onCardClicked = {
                onCardClicked(newsList[page].toNewsDetails(cyclicColor))
            }
        )
    }
}

@Composable
private fun SuccessUi(
    articleList: List<Article>,
    colorList: List<Color>,
    onCardClicked: (NewsDetailsNavArgs) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        NewsPager(articleList, colorList, onCardClicked)
    }
}


@Composable
private fun TopBar(modifier: Modifier,onSettingsClicked: () -> Unit) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "Briefly",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Icon(
            painter = painterResource(Res.drawable.ic_settings),
            contentDescription = "Settings",
            tint = Color.White,
            modifier = Modifier
                .padding(top = 4.dp)
                .size(24.dp)
                .clickable {onSettingsClicked() }
        )
    }
}
