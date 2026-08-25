package com.accidentaldeveloper.briefly.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookMarkScreen(){
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