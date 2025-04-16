package com.nativ.fetch.di

import com.nativ.fetch.data.remote.ItemApiService
import com.nativ.fetch.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://fetch-hiring.s3.amazonaws.com/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ItemApiService =
        retrofit.create(ItemApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService: ItemApiService): ItemRepository =
        ItemRepository(apiService)
}