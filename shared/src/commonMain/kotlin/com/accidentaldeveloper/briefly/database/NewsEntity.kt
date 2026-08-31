package com.accidentaldeveloper.briefly.database
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.accidentaldeveloper.briefly.model.Article
import com.accidentaldeveloper.briefly.model.Source


@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val newsId: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val sourceId: String?,
    val sourceName: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

fun NewsEntity.toNewsArticle(): Article {
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = Source(id = this.sourceId, name = this.sourceName),
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}

fun Article.stableId(): String {
    return this.url?.takeIf { it.isNotBlank() }
        ?: "${this.source?.id ?: this.source?.name}|${this.title}|${this.publishedAt}"
}

fun Article.toNewsEntity(): NewsEntity {
    return NewsEntity(
        newsId = this.stableId(),
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        sourceId = this.source?.id,
        sourceName = this.source?.name,
        title = this.title,
        url = this.url,
        urlToImage = this.urlToImage
    )
}
