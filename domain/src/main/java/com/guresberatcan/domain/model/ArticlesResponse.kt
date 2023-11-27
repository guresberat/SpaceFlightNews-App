package com.guresberatcan.domain.model

data class ArticlesResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<Article?>? = null
)