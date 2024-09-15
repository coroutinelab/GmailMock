package com.coroutinelab.data.di

import com.coroutinelab.data.remote.api.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val networkJson = Json { ignoreUnknownKeys = true }
    @Provides
    fun provideBaseUrl() = "https://66e4784bd2405277ed14692e.mockapi.io"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        url:String,
        loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit  =  Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)
}