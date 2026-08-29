package com.accidentaldeveloper.briefly.api

import com.accidentaldeveloper.briefly.api.ApiConstant.EVERYTHING
import com.accidentaldeveloper.briefly.api.ApiConstant.TOP_HEADLINES_ENDPOINT
import com.accidentaldeveloper.briefly.model.TopHeadlinesResponse
import com.accidentaldeveloper.briefly.network.BrieflyClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NewsApi(private val brieflyClient: BrieflyClient) {
    suspend fun getTopHeadlines(): TopHeadlinesResponse{
        return brieflyClient.newsApiClient.get(TOP_HEADLINES_ENDPOINT){
            parameter("country", "us")
        }.body<TopHeadlinesResponse>()
    }
    suspend fun getNewsOfSpecificType(query: String): TopHeadlinesResponse{
        return brieflyClient.newsApiClient.get(EVERYTHING){
            parameter("q",query)
        }.body()
    }
}