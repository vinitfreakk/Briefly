package com.accidentaldeveloper.briefly.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.accidentaldeveloper.briefly.ui.components.ErrorView
import com.accidentaldeveloper.briefly.ui.components.LoadingView

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val apiKey by settingsViewModel.apiKey.collectAsStateWithLifecycle()
    var draftApiKey by remember(apiKey) { mutableStateOf((apiKey as? SettingsUiState.Success)?.string.orEmpty()) }

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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            TextField(
                value = draftApiKey, onValueChange = {
                    draftApiKey = it
                }, maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
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
                    Text(text = "API KEY", color = Color.Gray)
                }, visualTransformation = PasswordVisualTransformation()
            )

            // Loading / Error feedback, driven by the sealed state
            when (apiKey) {
                is SettingsUiState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxWidth())
                }
                is SettingsUiState.Error -> {
                    ErrorView(
                        message = (apiKey as SettingsUiState.Error).error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is SettingsUiState.Success, SettingsUiState.Initial -> Unit
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.3f),
                enabled = apiKey !is SettingsUiState.Loading,
                onClick = {
                    settingsViewModel.saveApiKey(draftApiKey)
                },
                colors = ButtonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.White
                )
            ) {
                Text("Save", color = Color.White)
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
            text = "Settings",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}