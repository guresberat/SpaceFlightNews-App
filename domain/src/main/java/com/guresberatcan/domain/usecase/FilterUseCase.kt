package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case responsible for filtering articles based on a search query.
 *
 * @property repository The ArticleRepository for data access.
 */
class FilterUseCase @Inject constructor(private val repository: ArticleRepository) {

    /**
     * Invokes the use case to filter articles based on a search query.
     *
     * @param searchQuery The search query for filtering articles.
     * @return Flow of the filtered list of articles.
     */
    operator fun invoke(searchQuery: String): Flow<List<Article>> = repository.filter(searchQuery)

}