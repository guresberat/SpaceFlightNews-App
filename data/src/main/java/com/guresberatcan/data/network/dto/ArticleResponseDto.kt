package com.guresberatcan.data.network.dto

data class ArticlesResponseDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<ArticleDto?>? = null
)