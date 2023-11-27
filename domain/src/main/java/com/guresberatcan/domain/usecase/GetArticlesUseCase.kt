package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import com.guresberatcan.domain.utils.Resource
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val repository: ArticleRepository) {

    suspend operator fun invoke(): Resource<List<Article>> = repository.getArticles()

}