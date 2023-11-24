package com.guresberatcan.spaceflightnewsapp.data.model

data class ArticlesResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val result: List<Article?>? = null
)