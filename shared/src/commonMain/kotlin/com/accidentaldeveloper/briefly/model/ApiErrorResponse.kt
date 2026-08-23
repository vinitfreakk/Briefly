package com.accidentaldeveloper.briefly.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val code: String,
    val message: String,
    val status: String
)