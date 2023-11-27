package com.guresberatcan.spaceflightnewsapp.network.dto

import com.google.gson.annotations.SerializedName
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.utils.convertDate

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