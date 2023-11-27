package com.guresberatcan.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ArticleDatabase")
data class Article(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val imageUrl: String? = null,
    val newsSite: String? = null,
    val publishedAt: String? = null,
    val summary: String? = null,
    val title: String? = null,
    val updatedAt: String? = null,
    val url: String? = null,
    var isFavourite: Boolean = false
)