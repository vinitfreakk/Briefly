package com.accidentaldeveloper.briefly.Utils

import androidx.compose.ui.graphics.Color
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

fun getColorList(): List<Color> {
    return listOf(
        Color(0xFFFFF2C5), // Soft yellow
        Color(0xFFE1F1FF), // Soft blue
        Color(0xFFFCE4E2), // Soft pink
        Color(0xFFECE6FF), // Soft purple
        Color(0xFFE2F7E9), // Soft green
        Color(0xFFFFE5D9), // Soft peach
        Color(0xFFE0F7F6), // Soft mint
        Color(0xFFF5E6FF), // Soft lavender
        Color(0xFFFFEAF2), // Soft rose
        Color(0xFFFFF0DB), // Soft cream
        Color(0xFFE8F0FE), // Soft periwinkle
        Color(0xFFE9F5E1)  // Soft sage
    )
}


