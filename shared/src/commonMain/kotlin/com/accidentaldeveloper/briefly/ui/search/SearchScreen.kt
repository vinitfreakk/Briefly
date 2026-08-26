package com.accidentaldeveloper.briefly.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import briefly.shared.generated.resources.Res
import briefly.shared.generated.resources.ic_search_filled
import coil3.compose.AsyncImage
import com.accidentaldeveloper.briefly.Utils.cleanArticleText
import com.accidentaldeveloper.briefly.Utils.getColorList
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.navigation.NewsDetailsNavArgs
import com.accidentaldeveloper.briefly.navigation.toNewsDetails
import com.accidentaldeveloper.briefly.ui.components.ErrorView
import com.accidentaldeveloper.briefly.ui.components.LoadingView
import io.ktor.http.cio.Response
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchScreen(
    searchScreenViewModel: SearchViewModel,
    onNewsClicked: (newsDetails: NewsDetailsNavArgs) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val response by searchScreenViewModel.searchState.collectAsStateWithLifecycle()

    val onSearchClick = {
        searchScreenViewModel.searchNews(searchText)
    }

    val colorList = getColorList()


    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(16.dp)
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            SearchBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onSearchClick = onSearchClick
            )

            when (response) {
                SearchUiState.Initial -> Unit

                SearchUiState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxSize())
                }

                is SearchUiState.Success -> {
                    val response = (response as SearchUiState.Success).newsList
                    SuccessUi(response,colorList,onNewsClicked)
                }

                is SearchUiState.Error -> {
                    ErrorView(
                        modifier = Modifier.fillMaxSize(),
                        message = (response as SearchUiState.Error).error
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Search",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF232323),
                unfocusedContainerColor = Color(0xFF232323),
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            placeholder = {
                Text(
                    text = "Search news",
                    color = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick()
                }
            )
        )

        IconButton(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF232323)),
            onClick = onSearchClick
        ) {
            Icon(
                painter = painterResource(
                    Res.drawable.ic_search_filled
                ),
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun ListItem(
    article: Article,
    color: Color,
    onNewsClicked: (newsDetails: NewsDetailsNavArgs) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp))
            .clickable(onClick = {
                onNewsClicked(
                    article.toNewsDetails(
                        color,
                        content = article.content?.cleanArticleText()
                    )
                )
            }),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(120.dp).padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = article.title ?: "NA",
                maxLines = 4,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SuccessUi(response: List<Article>,colorList: List<Color>,onNewsClicked:(newsDetails: NewsDetailsNavArgs)-> Unit){
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(response.size) { index ->
            val color = colorList[index % colorList.size]
            ListItem(
                article = response[index],
                color,
                onNewsClicked = onNewsClicked
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

    }
}