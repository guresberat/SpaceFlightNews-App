package com.guresberatcan.domain.repository

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(): Resource<List<Article>>
    suspend fun updateArticle(id: Int, isFavourite: Boolean)

    fun filter(searchQuery: String): Flow<List<Article>>

    fun getArticleData(id: Int): Flow<Article>

}