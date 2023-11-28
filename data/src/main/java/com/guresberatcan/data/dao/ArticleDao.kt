package com.guresberatcan.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guresberatcan.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    /**
     * Retrieves a list of articles from the database, ordered by ID in descending order.
     *
     * @return A list of articles.
     */
    @Query("SELECT * FROM spaceflight_news_db ORDER BY id DESC")
    suspend fun getArticles(): List<Article>

    /**
     * Inserts or replaces articles in the database.
     *
     * @param articles The list of articles to be cached.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheArticles(articles: List<Article>)

    /**
     * Updates the favorite status of an article in the database.
     *
     * @param id The ID of the article to be updated.
     * @param isFavourite The new favorite status of the article.
     */
    @Query("UPDATE spaceflight_news_db SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun updateArticle(id: Int, isFavourite: Boolean)

    /**
     * Filters articles based on a search query and observes changes using Flow.
     *
     * @param searchQuery The search query for filtering articles.
     * @return Flow of the filtered list of articles.
     */
    @Query("SELECT * FROM spaceflight_news_db WHERE title LIKE '%' || :searchQuery || '%' OR summary LIKE '%' || :searchQuery || '%' ORDER BY id DESC")
    fun filter(searchQuery: String): Flow<List<Article>>

    /**
     * Retrieves a specific article's data based on its ID and observes changes using Flow.
     *
     * @param id The ID of the article to retrieve.
     * @return Flow of the article's data.
     */
    @Query("SELECT * FROM spaceflight_news_db WHERE id = :id")
    fun getArticleData(id: Int): Flow<Article>

}