package com.guresberatcan.spaceflightnewsapp.data.repository

import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(): Resource<List<Article>>
    suspend fun updateArticle(id: Int, isFavourite: Boolean)

    fun filter(searchQuery: String): Flow<List<Article>>

    fun getArticleData(id: Int): Flow<Article>

}