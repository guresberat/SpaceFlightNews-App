package com.guresberatcan.data.network

import com.guresberatcan.data.network.dto.ArticlesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for Space Flight-related API calls.
 */
interface SpaceFlightAPI {

    /**
     * Retrieves a list of articles from the Space Flight API.
     *
     * @param articlesToSkip The number of articles to skip (pagination).
     * @return A Response containing the ArticlesResponseDto with the list of articles.
     */
    @GET("articles")
    suspend fun getArticles(
        @Query("limit")
        articlesToSkip: Int = 50
    ): Response<ArticlesResponseDto>
}