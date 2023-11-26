package com.guresberatcan.spaceflightnewsapp.network

import com.guresberatcan.spaceflightnewsapp.network.dto.ArticlesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceFlightAPI {

    @GET("articles")
    suspend fun getArticles(
        @Query("limit")
        articlesToSkip: Int = 50
    ): Response<ArticlesResponseDto>
}