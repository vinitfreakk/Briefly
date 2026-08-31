package com.accidentaldeveloper.briefly.navigation

import androidx.compose.ui.graphics.Color
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.model.Source

data class NewsDetailsNavArgs(
    val backGroundColor: Color,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

fun Article.toNewsDetails(backGroundColor: Color,content: String?=this.content): NewsDetailsNavArgs {
    return NewsDetailsNavArgs(
        backGroundColor = backGroundColor,
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}

fun NewsDetailsNavArgs.toNewsDetails(): Article {
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}