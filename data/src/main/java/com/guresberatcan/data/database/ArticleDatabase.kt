package com.guresberatcan.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.domain.model.Article

/**
 * Room Database class for storing and accessing articles.
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {

    /**
     * Provides a DAO (Data Access Object) interface to perform database operations on articles.
     *
     * @return The ArticleDao interface.
     */
    abstract fun articleDao(): ArticleDao

}