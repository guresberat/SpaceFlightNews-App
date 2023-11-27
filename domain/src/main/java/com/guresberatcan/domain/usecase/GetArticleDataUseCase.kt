package com.guresberatcan.domain.usecase

import com.guresberatcan.domain.model.Article
import com.guresberatcan.domain.repository.ArticleRepository
import com.guresberatcan.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleDataUseCase @Inject constructor(private val repository: com.guresberatcan.domain.repository.ArticleRepository) {

    operator fun invoke(id : Int): Flow<Article> = repository.getArticleData(id)

}