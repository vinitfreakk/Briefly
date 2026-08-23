package com.accidentaldeveloper.briefly.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsApiException(
    val statusCode: String, override val message: String
): Exception(message)
