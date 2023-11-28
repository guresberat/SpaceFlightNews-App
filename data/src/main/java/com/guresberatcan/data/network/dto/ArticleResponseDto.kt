package com.guresberatcan.data.network.dto

/**
 * Data Transfer Object (DTO) representing the response from the network containing a list of articles.
 *
 * @property count The total count of articles in the response.
 * @property next The URL for the next page of articles, if available.
 * @property previous The URL for the previous page of articles, if available.
 * @property results The list of ArticleDto objects representing individual articles in the response.
 */
data class ArticlesResponseDto(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<ArticleDto?>? = null
)