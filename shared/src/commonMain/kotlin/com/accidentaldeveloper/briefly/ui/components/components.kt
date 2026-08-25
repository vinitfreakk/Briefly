package com.accidentaldeveloper.briefly.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/*
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LoadingIndicator(color = Color.DarkGray)
    }
}*/

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "loadingColorTransition")

    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.DarkGray,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "loadingColor"
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        LoadingIndicator(
            color = animatedColor
        )
    }
}

@Composable
fun ErrorView(modifier: Modifier = Modifier, message: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(text = message, color = Color.White)
    }
}
