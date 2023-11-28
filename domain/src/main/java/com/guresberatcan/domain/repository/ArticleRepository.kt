package com.guresberatcan.domain.repository

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling articles data.
 */
interface ArticleRepository {

    /**
     * Retrieves a list of articles.
     *
     * @return A Resource containing a list of articles or an error.
     */
    suspend fun getArticles(): Resource<List<Article>>

    /**
     * Updates the favorite status of an article.
     *
     * @param id The ID of the article to be updated.
     * @param isFavourite The new favorite status of the article.
     */
    suspend fun updateArticle(id: Int, isFavourite: Boolean)

    /**
     * Filters articles based on a search query using a Flow.
     *
     * @param searchQuery The search query for filtering articles.
     * @return Flow of the filtered list of articles.
     */
    fun filter(searchQuery: String): Flow<List<Article>>

    /**
     * Retrieves a specific article's data based on its ID using a Flow.
     *
     * @param id The ID of the article to retrieve.
     * @return Flow of the article's data.
     */
    fun getArticleData(id: Int): Flow<Article>

}