package com.example.movieapp.movieslist.model.repo

import com.example.movieapp.shared.model.Movie
import kotlinx.coroutines.flow.Flow


interface MovieListReposing {
    suspend fun getMostPopularMovies(pageNumber: Int): Flow<List<Movie>>
    suspend fun getTopRatedMovies(pageNumber: Int): Flow<List<Movie>>
}