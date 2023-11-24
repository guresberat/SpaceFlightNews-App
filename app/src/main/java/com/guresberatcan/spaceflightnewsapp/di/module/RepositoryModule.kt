package com.guresberatcan.spaceflightnewsapp.di.module

import com.guresberatcan.spaceflightnewsapp.data.dao.ArticleDao
import com.guresberatcan.spaceflightnewsapp.data.repository.ArticleRepository
import com.guresberatcan.spaceflightnewsapp.data.repository.ArticleRepositoryImpl
import com.guresberatcan.spaceflightnewsapp.network.SpaceFlightAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        spaceFlightAPI: SpaceFlightAPI,
        articleDao: ArticleDao
    ): ArticleRepository = ArticleRepositoryImpl(spaceFlightAPI, articleDao)
}