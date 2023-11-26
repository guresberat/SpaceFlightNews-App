package com.guresberatcan.spaceflightnewsapp.data.repository

import com.guresberatcan.spaceflightnewsapp.data.dao.ArticleDao
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.network.SpaceFlightAPI
import com.guresberatcan.spaceflightnewsapp.network.dto.toArticle
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import com.guresberatcan.spaceflightnewsapp.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val spaceFlightApi: SpaceFlightAPI,
    private val localDataSource: ArticleDao
) : ArticleRepository {
    override fun getArticles(): Flow<Resource<List<Article>>> {
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

    override suspend fun updateArticle(id: Int, isFavourite: Boolean) {
        localDataSource.updateArticle(id, isFavourite)
    }

    override fun filter(searchQuery: String): Flow<List<Article>> {
        return localDataSource.filter(searchQuery)
    }
}