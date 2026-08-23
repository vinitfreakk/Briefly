package com.accidentaldeveloper.briefly.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = koinViewModel()){
    val response = homeScreenViewModel.topHeadlinesResponse.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        Text(
            text = "Briefly",
            modifier = Modifier.statusBarsPadding().padding(horizontal = 16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }, containerColor = Color.Black) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color.Black)){
          Column(modifier = Modifier.padding(16.dp).fillMaxSize().background(Color.Black)){

          }
        }
    }
}