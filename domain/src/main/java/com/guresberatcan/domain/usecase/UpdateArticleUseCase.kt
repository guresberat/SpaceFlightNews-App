package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.repository.ArticleRepository
import javax.inject.Inject

/**
 * Use case responsible for updating the favorite status of an article.
 *
 * @property repository The ArticleRepository for data access.
 */
class UpdateArticleUseCase @Inject constructor(private val repository: ArticleRepository) {

    /**
     * Invokes the use case to update the favorite status of an article.
     *
     * @param id The ID of the article to be updated.
     * @param isFavourite The new favorite status of the article.
     */
    suspend operator fun invoke(id: Int, isFavourite: Boolean) =
        repository.updateArticle(id, isFavourite)

}