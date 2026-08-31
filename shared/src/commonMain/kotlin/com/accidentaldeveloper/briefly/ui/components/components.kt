package com.accidentaldeveloper.briefly.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import briefly.shared.generated.resources.Res
import briefly.shared.generated.resources.ic_bookmark_filled
import briefly.shared.generated.resources.ic_bookmark_outline
import briefly.shared.generated.resources.ic_browser
import briefly.shared.generated.resources.ic_share
import org.jetbrains.compose.resources.painterResource

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

@Composable
fun ArticleActionRow(
    modifier: Modifier,
    isBookMarked: Boolean,
    onBrowserClicked: () -> Unit,
    onBookMarkClicked: () -> Unit,
    onShareClicked: () -> Unit,
    iconColor: Color = Color.DarkGray,
    iconBackGroundColor: Color = Color.Black.copy(alpha = 0.12f)
) {
    Row(modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        IconButton(
            modifier = Modifier.clip(CircleShape).background(iconBackGroundColor),
            onClick = { onBrowserClicked() }) {
            Icon(
                tint = iconColor,
                modifier = Modifier.size(28.dp),
                painter = painterResource(resource = Res.drawable.ic_browser),
                contentDescription = null
            )
        }
        IconButton(
            modifier = Modifier.clip(CircleShape).background(iconBackGroundColor),
            onClick = { onShareClicked() }) {
            Icon(
                tint = iconColor,
                modifier = Modifier.size(28.dp),
                painter = painterResource(resource = Res.drawable.ic_share),
                contentDescription = null
            )
        }
        IconButton(
            modifier = Modifier.clip(CircleShape).background(iconBackGroundColor),
            onClick = { onBookMarkClicked() }) {
            Icon(
                tint = iconColor,
                modifier = Modifier.size(28.dp),
                painter = painterResource(resource = if (isBookMarked) Res.drawable.ic_bookmark_filled else Res.drawable.ic_bookmark_outline),
                contentDescription = null
            )
        }
    }
}
