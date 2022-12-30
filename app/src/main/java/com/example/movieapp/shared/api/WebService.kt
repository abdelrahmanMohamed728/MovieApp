package com.example.movieapp.shared.api

import com.example.movieapp.movieslist.model.repo.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MoviesListResponse>

    @GET("movie/popular")
    fun getMostPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MoviesListResponse>

}