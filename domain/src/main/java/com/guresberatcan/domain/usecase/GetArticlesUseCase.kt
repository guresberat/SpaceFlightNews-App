package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import com.guresberatcan.domain.utils.Resource
import javax.inject.Inject

/**
 * Use case responsible for retrieving a list of articles.
 *
 * @property repository The ArticleRepository for data access.
 */
class GetArticlesUseCase @Inject constructor(private val repository: ArticleRepository) {

    /**
     * Invokes the use case to retrieve a list of articles.
     *
     * @return A Resource containing a list of articles or an error.
     */
    suspend operator fun invoke(): Resource<List<Article>> = repository.getArticles()

}