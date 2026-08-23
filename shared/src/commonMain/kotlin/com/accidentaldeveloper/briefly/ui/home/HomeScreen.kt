package com.accidentaldeveloper.briefly.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel) {
    val response = homeScreenViewModel.topHeadlinesResponse.collectAsStateWithLifecycle()
    val newsList = homeScreenViewModel.getNewsTopics()
    var selectedTopic by remember { mutableStateOf("Trending") }
    Scaffold(topBar = {
        Text(
            text = "Briefly",
            modifier = Modifier.statusBarsPadding().padding(16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }, containerColor = Color.Black) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color.Black)) {
            Column(modifier = Modifier.padding(16.dp).fillMaxSize().background(Color.Black)) {
                NewsTopics(newsList,selectedTopic, onSelectedTopic = {selectedTopic=it})
            }
        }
    }
}

@Composable
fun NewsTopics(newsList:List<String>,selectedTopic: String,onSelectedTopic:(String)-> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().background(Color.Transparent)
    ) {
        items(newsList.size) {index->
            val topic = newsList[index]
            val isSelected = if(topic == selectedTopic) true else false
            Text(
                text = topic,
                fontSize = if(isSelected) 28.sp else 18.sp,
                fontWeight = FontWeight.Normal,
                color = if(isSelected) Color.White else Color.DarkGray,
                modifier = Modifier.clickable(onClick = {
                    onSelectedTopic(topic)
                })
            )
        }
    }
}