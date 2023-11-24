package com.guresberatcan.spaceflightnewsapp.network

import com.guresberatcan.spaceflightnewsapp.data.model.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceFlightAPI {

    @GET("articles")
    suspend fun getArticles(
        @Query("_start")
        articlesToSkip: Int = 0
    ): Response<ArticlesResponse>
}