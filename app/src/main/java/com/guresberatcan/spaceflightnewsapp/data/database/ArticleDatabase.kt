package com.guresberatcan.spaceflightnewsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guresberatcan.spaceflightnewsapp.data.dao.ArticleDao
import com.guresberatcan.spaceflightnewsapp.data.model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "ArticleDatabase"
    }
}