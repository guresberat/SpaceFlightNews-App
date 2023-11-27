package com.guresberatcan.data.repository

import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.data.network.SpaceFlightAPI
import com.guresberatcan.data.network.dto.toArticle
import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import com.guresberatcan.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val spaceFlightApi: SpaceFlightAPI,
    private val localDataSource: ArticleDao
) : ArticleRepository {
    override suspend fun getArticles(): Resource<List<Article>> {
        return try {
            val response = spaceFlightApi.getArticles()
            val articles = response.body()?.results?.filterNotNull() ?: emptyList()
            localDataSource.getArticles().map {
                articles.find { articleDto -> articleDto.id == it.id }?.isFavourite =
                    it.isFavourite
            }.also {
                localDataSource.cacheArticles(articles.map { articleDto ->
                    articleDto.toArticle()
                })
            }
            Resource.Success(articles.map { it.toArticle() })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage.orEmpty(), localDataSource.getArticles())
        }
    }

    override suspend fun updateArticle(id: Int, isFavourite: Boolean) {
        localDataSource.updateArticle(id, isFavourite)
    }

    override fun filter(searchQuery: String): Flow<List<Article>> {
        return localDataSource.filter(searchQuery)
    }

    override fun getArticleData(id: Int): Flow<Article> {
        return localDataSource.getArticleData(id)
    }
}