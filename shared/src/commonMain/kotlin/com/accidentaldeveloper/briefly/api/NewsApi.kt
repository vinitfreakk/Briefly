package com.accidentaldeveloper.briefly.api

import com.accidentaldeveloper.briefly.api.ApiConstant.TOP_HEADLINES_ENDPOINT
import com.accidentaldeveloper.briefly.model.TopHeadlinesResponse
import com.accidentaldeveloper.briefly.network.newsApiClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NewsApi {
    suspend fun getTopHeadlines(): TopHeadlinesResponse{
        return newsApiClient.get(TOP_HEADLINES_ENDPOINT){
            parameter("country", "us")
            parameter("apiKey", "")
        }.body<TopHeadlinesResponse>()
    }
}