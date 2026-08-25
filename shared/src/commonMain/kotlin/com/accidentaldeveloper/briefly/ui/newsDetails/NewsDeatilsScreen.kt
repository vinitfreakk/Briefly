package com.accidentaldeveloper.briefly.ui.newsDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import briefly.shared.generated.resources.Res
import briefly.shared.generated.resources.ic_arrow_back
import coil3.compose.AsyncImage
import com.accidentaldeveloper.briefly.Utils.cleanArticleText
import com.accidentaldeveloper.briefly.Utils.toRelativeTime
import com.accidentaldeveloper.briefly.navigation.NewsDetailsNavArgs
import org.jetbrains.compose.resources.painterResource

@Composable
fun NewsDetailsScreen(newsDetails: NewsDetailsNavArgs,onBackClicked:()-> Unit) {
    Scaffold(
        containerColor = newsDetails.backGroundColor,
        topBar = {
            TopBar(modifier = Modifier.statusBarsPadding().padding(16.dp),onBackClicked)
        },
        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(24.dp))
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = newsDetails.title ?: "N/A",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 40.sp,
                lineHeight = 50.sp
            )


            Spacer(modifier = Modifier.height(12.dp))

            // Timestamp — small, muted, functions as metadata not content
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = newsDetails.publishedAt?.toRelativeTime() ?: "",
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
                text = newsDetails.author ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Body — comfortable reading size, relaxed line height for a paragraph of text
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = newsDetails.content?.cleanArticleText() ?: newsDetails.description?.cleanArticleText()?:"",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black.copy(alpha = 0.85f),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(20.dp))

            AsyncImage(
                model = newsDetails.urlToImage,
                contentDescription = newsDetails.title,
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )

        }
    }
}

@Composable
private fun TopBar(modifier: Modifier,onBackClicked: () -> Unit) {
    Row(modifier = modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier.size(28.dp).clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.12f)), onClick = {onBackClicked()}) {
            Icon(painter = painterResource(Res.drawable.ic_arrow_back), contentDescription = null)
        }
    }
}