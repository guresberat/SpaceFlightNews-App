package com.guresberatcan.spaceflightnewsapp.data.repository

import com.guresberatcan.spaceflightnewsapp.data.model.ArticlesResponse
import retrofit2.Response

interface ArticleRepository {
    suspend fun getArticles(): Response<ArticlesResponse>

}