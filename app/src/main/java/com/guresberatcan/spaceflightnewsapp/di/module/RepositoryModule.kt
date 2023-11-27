package com.guresberatcan.spaceflightnewsapp.di.module

import com.guresberatcan.data.repository.ArticleRepositoryImpl
import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.data.network.SpaceFlightAPI
import com.guresberatcan.domain.repository.ArticleRepository
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
    ): ArticleRepository =
        ArticleRepositoryImpl(spaceFlightAPI, articleDao)
}