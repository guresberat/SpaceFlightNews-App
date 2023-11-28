package com.guresberatcan.spaceflightnewsapp.di.module

import android.content.Context
import androidx.room.Room
import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.data.database.ArticleDatabase
import com.guresberatcan.domain.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    /**
     * Provides a singleton instance of the ArticleDatabase using Room.
     *
     * @param context The application context.
     * @return Singleton instance of ArticleDatabase.
     */
    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides a singleton instance of the ArticleDao for local data access.
     *
     * @param articleDatabase The ArticleDatabase instance.
     * @return Singleton instance of ArticleDao.
     */
    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }
}