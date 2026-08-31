package com.accidentaldeveloper.briefly.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT EXISTS(SELECT 1 FROM news WHERE newsId = :newsId)")
    fun isBookMarked(newsId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNews(newsEntity: NewsEntity)

    @Query("DELETE FROM news WHERE newsId = :newsId")
    suspend fun deleteNewsById(newsId: String)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<NewsEntity>>
}

