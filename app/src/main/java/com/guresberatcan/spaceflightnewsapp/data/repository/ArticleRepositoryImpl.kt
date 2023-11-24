package com.guresberatcan.spaceflightnewsapp.data.repository

import com.guresberatcan.spaceflightnewsapp.data.dao.ArticleDao
import com.guresberatcan.spaceflightnewsapp.data.model.ArticlesResponse
import com.guresberatcan.spaceflightnewsapp.network.SpaceFlightAPI
import retrofit2.Response
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val spaceFlightApi: SpaceFlightAPI,
    private val localDataSource: ArticleDao
) : ArticleRepository {
    override suspend fun getArticles(): Response<ArticlesResponse> {
        return spaceFlightApi.getArticles()
    }
}