package com.guresberatcan.data.network.dto

import com.guresberatcan.domain.model.ArticlesResponse
import okhttp3.internal.toImmutableList

data class ArticlesResponseDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<ArticleDto?>? = null
)

fun ArticlesResponseDto.toDomain(): ArticlesResponse {
    return ArticlesResponse(
        count = count,
        next = next,
        previous = previous,
        results = results?.map { it?.toArticle() }?.toImmutableList()
    )
}