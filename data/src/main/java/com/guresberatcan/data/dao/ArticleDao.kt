package com.guresberatcan.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guresberatcan.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM ArticleDatabase")
    suspend fun getArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheArticles(articles: List<Article>)

    @Query("UPDATE ArticleDatabase SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun updateArticle(id: Int, isFavourite: Boolean)

    @Query("SELECT * FROM ArticleDatabase WHERE title LIKE '%' || :searchQuery || '%' OR summary LIKE '%' || :searchQuery || '%'")
    fun filter(searchQuery: String): Flow<List<Article>>

    @Query("SELECT * FROM ArticleDatabase WHERE id = :id")
    fun getArticleData(id: Int): Flow<Article>

}