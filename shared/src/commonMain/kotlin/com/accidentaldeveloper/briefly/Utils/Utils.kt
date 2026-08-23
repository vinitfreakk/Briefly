package com.accidentaldeveloper.briefly.Utils

import kotlin.time.Clock
import kotlin.time.Instant

fun String.cleanArticleText(): String {
    return this
        .substringBefore("[+")   // remove "[+5142 chars]" truncation notice
        .replace(Regex("<[^>]*>"), "") // remove HTML tags like <ul><li>
        .trim()
}

fun String.toRelativeTime(): String {
    return try {
        val published = Instant.parse(this)
        val now = Clock.System.now()
        val minutes = (now - published).inWholeMinutes

        when {
            minutes < 1 -> "Just now"
            minutes < 60 -> "$minutes min ago"
            minutes < 1440 -> "${minutes / 60} hr ago"
            else -> "${minutes / 1440} days ago"
        }
    } catch (e: Exception) {
        this
    }
}