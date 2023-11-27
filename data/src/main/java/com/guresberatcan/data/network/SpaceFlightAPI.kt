package com.guresberatcan.data.network

import com.guresberatcan.data.network.dto.ArticlesResponseDto
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