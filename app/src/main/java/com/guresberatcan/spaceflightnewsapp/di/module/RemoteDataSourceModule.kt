package com.guresberatcan.spaceflightnewsapp.di.module

import com.google.gson.GsonBuilder
import com.guresberatcan.data.network.SpaceFlightAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): SpaceFlightAPI {
        val gson = GsonBuilder()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
        return retrofit.build().create(SpaceFlightAPI::class.java)
    }

    private val logging = HttpLoggingInterceptor()
    private val level = logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(level)
        .build()

    private const val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"
}