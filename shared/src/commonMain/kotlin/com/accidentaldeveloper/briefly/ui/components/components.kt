package com.accidentaldeveloper.briefly.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import briefly.shared.generated.resources.Res
import briefly.shared.generated.resources.ic_bookmark_filled
import briefly.shared.generated.resources.ic_bookmark_outline
import briefly.shared.generated.resources.ic_browser
import briefly.shared.generated.resources.ic_share
import com.accidentaldeveloper.briefly.Utils.cleanArticleText
import com.accidentaldeveloper.briefly.Utils.toRelativeTime
import com.accidentaldeveloper.briefly.platform.Share
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
    isBookMarkIconVisible: Boolean = true,
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
        if(isBookMarkIconVisible){
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
}

@Composable
fun PagerItem(
    headline: String,
    content: String,
    author: String,
    time: String,
    color: Color,
    modifier: Modifier,
    share:()-> Unit,
    onCardClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onCardClicked)
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
                isBookMarkIconVisible = false,
                onBookMarkClicked = { },
                onShareClicked = {share()},
                onBrowserClicked = { }
            )
        }

    }

}
