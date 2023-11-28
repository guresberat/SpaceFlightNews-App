package com.guresberatcan.domain.model

/**
 * Data class representing a response containing a list of articles in the domain model.
 *
 * @property count The total count of articles in the response.
 * @property next The URL for the next page of articles, if available.
 * @property previous The URL for the previous page of articles, if available.
 * @property results The list of Article objects representing individual articles in the response.
 */
data class ArticlesResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<Article?>? = null
)