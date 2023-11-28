package com.guresberatcan.spaceflightnewsapp.di.module

import com.guresberatcan.data.repository.ArticleRepositoryImpl
import com.guresberatcan.data.dao.ArticleDao
import com.guresberatcan.data.network.SpaceFlightAPI
import com.guresberatcan.data.util.NetworkConnectivityManager
import com.guresberatcan.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    /**
     * Provides a singleton instance of the ArticleRepository interface.
     *
     * @param spaceFlightAPI The SpaceFlightAPI for remote data access.
     * @param articleDao The ArticleDao for local data access.
     * @param networkConnectivityManager The NetworkConnectivityManager for checking the Internet connection.
     * @return Singleton instance of ArticleRepository.
     */
    @Provides
    @Singleton
    fun provideRepository(
        spaceFlightAPI: SpaceFlightAPI,
        articleDao: ArticleDao,
        networkConnectivityManager: NetworkConnectivityManager
    ): ArticleRepository =
        ArticleRepositoryImpl(spaceFlightAPI, articleDao, networkConnectivityManager)
}