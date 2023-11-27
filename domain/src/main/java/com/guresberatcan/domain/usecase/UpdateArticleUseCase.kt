package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.repository.ArticleRepository
import javax.inject.Inject

class UpdateArticleUseCase @Inject constructor(private val repository: com.guresberatcan.domain.repository.ArticleRepository) {

    suspend operator fun invoke(id:Int, isFavourite:Boolean) = repository.updateArticle(id,isFavourite)

}