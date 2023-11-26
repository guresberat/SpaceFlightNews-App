package com.guresberatcan.spaceflightnewsapp.data.usecase

import com.guresberatcan.spaceflightnewsapp.data.repository.ArticleRepository
import javax.inject.Inject

class UpdateArticleUseCase @Inject constructor(private val repository: ArticleRepository) {

    suspend operator fun invoke(id:Int, isFavourite:Boolean) = repository.updateArticle(id,isFavourite)

}