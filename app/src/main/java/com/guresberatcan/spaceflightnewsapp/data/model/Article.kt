package com.guresberatcan.spaceflightnewsapp.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    val events: List<Any>? = null,
    val featured: Boolean? = null,
    val id: Int? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    val newsSite: String? = null,
    val publishedAt: String? = null,
    val summary: String? = null,
    val title: String? = null,
    val updatedAt: String? = null,
    val url: String? = null
)