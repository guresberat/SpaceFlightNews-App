package com.guresberatcan.spaceflightnewsapp.data.usecase

import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.data.repository.ArticleRepository
import com.guresberatcan.spaceflightnewsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FilterUseCase @Inject constructor(private val repository: ArticleRepository) {

    operator fun invoke(searchQuery: String): Flow<List<Article>> = repository.filter(searchQuery)

}