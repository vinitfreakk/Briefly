package com.accidentaldeveloper.briefly

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.accidentaldeveloper.briefly.navigation.AppNavigation

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}