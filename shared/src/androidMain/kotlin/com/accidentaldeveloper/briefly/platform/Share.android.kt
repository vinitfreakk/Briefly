package com.accidentaldeveloper.briefly.platform

import android.content.Context
import android.content.Intent

class AndroidShareManager(private val context: Context) : ShareManager {
    override fun share(link: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
        }

        val chooserIntent = Intent.createChooser(intent, "Share via").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(chooserIntent)
    }
}