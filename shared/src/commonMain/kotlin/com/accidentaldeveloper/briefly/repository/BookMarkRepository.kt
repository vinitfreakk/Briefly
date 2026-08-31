package com.accidentaldeveloper.briefly.repository

import com.accidentaldeveloper.briefly.database.NewsDao
import com.accidentaldeveloper.briefly.database.NewsEntity
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {
    suspend fun isBookMarked(newsId: String): Boolean

    suspend fun saveNews(newsEntity: NewsEntity)

    suspend fun deleteBookMark(newsId: String)

    suspend fun getAllNews(): Flow<List<NewsEntity>>
}

class BookMarkRepositoryImpl(private val newsDao: NewsDao): BookMarkRepository {
    override suspend fun isBookMarked(newsId: String): Boolean {
        return newsDao.isBookMarked(newsId)
    }

    override suspend fun saveNews(newsEntity: NewsEntity) {
        newsDao.saveNews(newsEntity)
    }

    override suspend fun deleteBookMark(newsId: String) {
        newsDao.deleteNewsById(newsId)
    }

    override suspend fun getAllNews(): Flow<List<NewsEntity>> {
        return newsDao.getAllNews()
    }
}