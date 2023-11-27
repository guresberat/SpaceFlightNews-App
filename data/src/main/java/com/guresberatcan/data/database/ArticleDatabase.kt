package com.guresberatcan.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.domain.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "ArticleDatabase"
    }
}