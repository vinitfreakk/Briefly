package com.accidentaldeveloper.briefly.ui.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import briefly.shared.generated.resources.Res
import briefly.shared.generated.resources.ic_search_filled
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchScreen() {
    var searchText by remember { mutableStateOf("") }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
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
                    }
                )

                IconButton(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF232323)),
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_search_filled),
                        contentDescription = "Search",
                        tint = Color.White
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
            text = "Search",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}