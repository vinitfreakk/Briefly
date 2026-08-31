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
                when (response) {
                    is TopHeadLinesUiState.Error -> {
                        ErrorView(
                            modifier = Modifier.fillMaxSize(),
                            message = (response as TopHeadLinesUiState.Error).error
                        )
                    }

                    TopHeadLinesUiState.Initial -> Unit
                    TopHeadLinesUiState.Loading -> {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }

                    is TopHeadLinesUiState.Success -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            SuccessUi(
                                newsList = newsList,
                                articleList = (response as TopHeadLinesUiState.Success).topHeadlinesResponse,
                                selectedTopic = selectedTopic,
                                onSelectedTopic = {
                                    selectedTopic = it
                                    if (it == TRENDING_TOPIC) {
                                        homeScreenViewModel.getTopHeadlines()
                                    } else {
                                        homeScreenViewModel.getNewsOfSpecificType(it)
                                    }

                                },
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
private fun PagerItem(
    headline: String,
    content: String,
    author: String,
    time: String,
    color: Color,
    onCardClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onCardClicked)
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1C)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .background(color)
                    .fillMaxSize()
                    .padding(24.dp),
            ) {
                // Headline — largest, tightest line height for a punchy masthead feel
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = headline,
                    fontSize = 26.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.3).sp, // slight tightening reads better at large bold sizes
                    maxLines = 3,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Timestamp — small, muted, functions as metadata not content
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = time.toRelativeTime(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.2.sp,
                    color = Color.Black.copy(alpha = 0.55f),
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Author block — label + name, label de-emphasized, name carries weight
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "PUBLISHED BY",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp, // wider tracking on small caps-style labels aids legibility
                    color = Color.Black.copy(alpha = 0.45f),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = author,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Body — comfortable reading size, relaxed line height for a paragraph of text
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = content.cleanArticleText(),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    maxLines = 7,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black.copy(alpha = 0.85f),
                    overflow = TextOverflow.Ellipsis
                )
            }
            ArticleActionRow(
                modifier = Modifier.align(Alignment.BottomEnd),
                isBookMarked = false,
                onBookMarkClicked = { },
                onShareClicked = { },
                onBrowserClicked = { }
            )
        }

    }

}


@Composable
private fun SuccessUi(
    newsList: List<String>,
    articleList: List<Article>,
    selectedTopic: String,
    onSelectedTopic: (String) -> Unit,
    colorList: List<Color>,
    onCardClicked: (NewsDetailsNavArgs) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        NewsTopics(
            newsList,
            selectedTopic,
            onSelectedTopic = onSelectedTopic  // just pass it straight through
        )
        Spacer(modifier = Modifier.height(32.dp))
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
