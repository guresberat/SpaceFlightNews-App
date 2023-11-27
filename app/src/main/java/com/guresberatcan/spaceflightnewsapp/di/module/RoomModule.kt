package com.guresberatcan.spaceflightnewsapp.di.module

import android.content.Context
import androidx.room.Room
import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.data.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ArticleDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }
}