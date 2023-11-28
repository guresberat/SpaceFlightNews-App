package com.guresberatcan.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guresberatcan.domain.utils.Constants

/**
 * Data class representing an article entity in the domain model.
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
@Entity(tableName = Constants.DATABASE_NAME)
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