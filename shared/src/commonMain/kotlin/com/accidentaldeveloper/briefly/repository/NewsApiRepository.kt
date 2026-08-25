package com.accidentaldeveloper.briefly.repository

import com.accidentaldeveloper.briefly.api.NewsApi
import com.accidentaldeveloper.briefly.model.TopHeadlinesResponse

interface NewsApiRepository {
    suspend fun getTopHeadLines():TopHeadlinesResponse

    suspend fun getNewsOfSpecificType(query: String):TopHeadlinesResponse
}

class NewsApiRepositoryImpl(private val newsApi: NewsApi): NewsApiRepository{
    override suspend fun getTopHeadLines(): TopHeadlinesResponse {
       return newsApi.getTopHeadlines()
    }

    override suspend fun getNewsOfSpecificType(query: String):TopHeadlinesResponse {
        return newsApi.getNewsOfSpecificType(query)
    }

}