package com.guresberatcan.spaceflightnewsapp.data.usecase

import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.data.repository.ArticleRepository
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val repository: ArticleRepository) {

    suspend operator fun invoke(): Resource<List<Article?>?> {
        val response = repository.getArticles()
        return if (response.isSuccessful) {
            Resource.Success(response.body()?.results)
        } else {
            Resource.Failure(response.code(), response.message())
        }
    }
}