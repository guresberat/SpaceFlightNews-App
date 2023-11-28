package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case responsible for retrieving data of a specific article based on its ID.
 *
 * @property repository The ArticleRepository for data access.
 */
class GetArticleDataUseCase @Inject constructor(private val repository: ArticleRepository) {

    /**
     * Invokes the use case to retrieve the data of a specific article.
     *
     * @param id The ID of the article to retrieve.
     * @return Flow of the article's data.
     */
    operator fun invoke(id: Int): Flow<Article> = repository.getArticleData(id)

}