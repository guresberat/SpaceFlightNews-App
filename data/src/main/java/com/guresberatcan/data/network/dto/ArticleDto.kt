package com.guresberatcan.data.network.dto

import com.google.gson.annotations.SerializedName
import com.guresberatcan.data.util.convertDate
import com.guresberatcan.domain.model.Article

/**
 * Data Transfer Object (DTO) representing an Article received from the network.
 *
 * @property id The unique identifier of the article.
 * @property imageUrl The URL of the article's image.
 * @property newsSite The news site where the article is published.
 * @property publishedAt The date and time when the article was published.
 * @property summary A brief summary or description of the article.
 * @property title The title of the article.
 * @property updatedAt The date and time when the article was last updated.
 * @property url The URL of the article.
 * @property isFavourite Indicates whether the article is marked as a favorite.
 */
data class ArticleDto(
    val id: Int? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("news_site")
    val newsSite: String? = null,
    @SerializedName("published_at")
    val publishedAt: String? = null,
    val summary: String? = null,
    val title: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    val url: String? = null,
    var isFavourite: Boolean = false
)

/**
 * Extension function to convert an ArticleDto to an Article domain model.
 *
 * @return The Article domain model.
 */
fun ArticleDto.toArticle(): Article {
    return Article(
        id = id,
        imageUrl = imageUrl,
        newsSite = newsSite,
        publishedAt = publishedAt?.convertDate(),
        summary = summary,
        title = title,
        updatedAt = updatedAt,
        url = url,
        isFavourite = isFavourite
    )
}