package com.guresberatcan.spaceflightnewsapp.data.model

data class Article(
    val events: List<Any>? = null,
    val featured: Boolean? = null,
    val id: Int? = null,
    val imageUrl: String? = null,
    val launches: List<Launche?>? = null,
    val newsSite: String? = null,
    val publishedAt: String? = null,
    val summary: String? = null,
    val title: String? = null,
    val updatedAt: String? = null,
    val url: String? = null
)