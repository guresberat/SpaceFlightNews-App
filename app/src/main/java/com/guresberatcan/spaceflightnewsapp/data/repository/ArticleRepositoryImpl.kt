package com.guresberatcan.spaceflightnewsapp.data.repository

import com.guresberatcan.spaceflightnewsapp.data.dao.ArticleDao
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.network.SpaceFlightAPI
import com.guresberatcan.spaceflightnewsapp.network.dto.toArticle
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import com.guresberatcan.spaceflightnewsapp.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
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

/*
     fun getArticless(): Flow<Resource<List<Article>>> {
        return networkBoundResource(
            query = { localDataSource.getArticles() },
            fetch = { spaceFlightApi.getArticles() },
            saveFetchedResult = { response ->
                val launches = response.body()?.results?.filterNotNull() ?: emptyList()
                localDataSource.getArticles().map { articleList ->
                    articleList.forEach {
                        launches.find { articleDto -> articleDto.id == it.id }?.isFavourite =
                            it.isFavourite
                    }
                }
                localDataSource.cacheArticles(launches.map { articleDto ->
                    articleDto.toArticle()
                })
            }
        )
    }
*/
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