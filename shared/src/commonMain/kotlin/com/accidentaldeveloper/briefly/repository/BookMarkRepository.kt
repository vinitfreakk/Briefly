package com.accidentaldeveloper.briefly.repository

import com.accidentaldeveloper.briefly.database.NewsDao
import com.accidentaldeveloper.briefly.database.NewsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface BookMarkRepository {
    fun isBookMarked(newsId: String): Flow<Boolean>

    suspend fun toggleNews(newsEntity: NewsEntity)

    suspend fun deleteBookMark(newsId: String)

    fun getAllNews(): Flow<List<NewsEntity>>
}

class BookMarkRepositoryImpl(private val newsDao: NewsDao): BookMarkRepository {
    override fun isBookMarked(newsId: String): Flow<Boolean> {
        return newsDao.isBookMarked(newsId)
    }

    override suspend fun toggleNews(newsEntity: NewsEntity) {
        val isBookMarked = newsDao.isBookMarked(newsEntity.newsId).first()
        if(isBookMarked){
            newsDao.deleteNewsById(newsEntity.newsId)
        }else{
            newsDao.saveNews(newsEntity)
        }

    }

    override suspend fun deleteBookMark(newsId: String) {
        newsDao.deleteNewsById(newsId)
    }

    override fun getAllNews(): Flow<List<NewsEntity>> {
        return newsDao.getAllNews()
    }
}