package com.example.movieapp.shared.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val baseUrl =  "https://api.themoviedb.org/3/"


    @Provides
    @Singleton
    fun retrofitClient(): BaseRetrofitClient {
        return BaseRetrofitClient.getInstance(null, baseUrl)
    }

}