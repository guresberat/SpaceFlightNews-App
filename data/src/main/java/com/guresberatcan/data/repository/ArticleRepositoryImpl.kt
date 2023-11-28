package com.guresberatcan.data.repository

import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.data.network.SpaceFlightAPI
import com.guresberatcan.data.network.dto.toArticle
import com.guresberatcan.data.util.NetworkConnectivityManager
import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import com.guresberatcan.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of the ArticleRepository interface for handling articles data.
 *
 * @property spaceFlightApi The SpaceFlightAPI for remote data access.
 * @property localDataSource The ArticleDao for local data access.
 * @property connectivityManager The NetworkConnectivityManager for checking the Internet connection.
 */
class ArticleRepositoryImpl @Inject constructor(
    private val spaceFlightApi: SpaceFlightAPI,
    private val localDataSource: ArticleDao,
    private val connectivityManager: NetworkConnectivityManager
) : ArticleRepository {

    /**
     * Retrieves a list of articles from the Space Flight API and updates the local database.
     *
     * @return A Resource containing a list of articles or an error.
     */
    override suspend fun getArticles(): Resource<List<Article>> {
        return if (connectivityManager.isConnectedToNetwork()) {
            try {
                // Fetch articles from the remote API
                val response = spaceFlightApi.getArticles()
                val articles = response.body()?.results?.filterNotNull() ?: emptyList()

                // Map the local favorite status to the fetched articles and update the local database
                localDataSource.getArticles().map {
                    articles.find { articleDto -> articleDto.id == it.id }?.isFavourite =
                        it.isFavourite
                }.also {
                    localDataSource.cacheArticles(articles.map { articleDto ->
                        articleDto.toArticle()
                    })
                }

                // Return the list of articles as a success resource
                Resource.Success(articles.map { it.toArticle() })
            } catch (e: Exception) {
                // Return an error resource if an exception occurs
                Resource.Error(e.localizedMessage.orEmpty(), localDataSource.getArticles())
            }
        } else {
            // Return an error resource if network is not available
            Resource.Error("No internet connection", localDataSource.getArticles())
        }
    }

    /**
     * Updates the favorite status of an article in the local database.
     *
     * @param id The ID of the article to be updated.
     * @param isFavourite The new favorite status of the article.
     */
    override suspend fun updateArticle(id: Int, isFavourite: Boolean) {
        localDataSource.updateArticle(id, isFavourite)
    }

    /**
     * Filters articles based on a search query using a Flow.
     *
     * @param searchQuery The search query for filtering articles.
     * @return Flow of the filtered list of articles.
     */
    override fun filter(searchQuery: String): Flow<List<Article>> {
        return localDataSource.filter(searchQuery)
    }

    /**
     * Retrieves a specific article's data based on its ID using a Flow.
     *
     * @param id The ID of the article to retrieve.
     * @return Flow of the article's data.
     */
    override fun getArticleData(id: Int): Flow<Article> {
        return localDataSource.getArticleData(id)
    }
}