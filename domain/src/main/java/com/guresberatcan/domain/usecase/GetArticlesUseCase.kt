package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import com.guresberatcan.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val repository: com.guresberatcan.domain.repository.ArticleRepository) {

    suspend operator fun invoke(): com.guresberatcan.domain.utils.Resource<List<Article>> = repository.getArticles()

}