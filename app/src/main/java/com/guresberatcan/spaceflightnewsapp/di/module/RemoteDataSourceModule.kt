package com.guresberatcan.spaceflightnewsapp.di.module

import com.google.gson.GsonBuilder
import com.guresberatcan.data.network.SpaceFlightAPI
import com.guresberatcan.domain.utils.Constants.BASE_URL
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

    /**
     * Provides a singleton instance of the SpaceFlightAPI interface for remote data access.
     *
     * @return Singleton instance of SpaceFlightAPI.
     */
    @Singleton
    @Provides
    fun provideRemoteDataSource(): SpaceFlightAPI {
        // Create a Gson instance
        val gson = GsonBuilder()
            .create()

        // Create a Retrofit instance with the specified base URL, Gson converter, and OkHttpClient
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)

        // Build and return the SpaceFlightAPI instance
        return retrofit.build().create(SpaceFlightAPI::class.java)
    }

    // Create a logging interceptor for HTTP request/response logging
    private val logging = HttpLoggingInterceptor()

    // Set the logging level to include request and response details
    private val level = logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create an OkHttpClient with the logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(level)
        .build()

}