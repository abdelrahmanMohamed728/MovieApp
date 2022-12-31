package com.example.movieapp.movieslist.model.repo

import com.example.movieapp.shared.model.Movie
import com.example.movieapp.shared.api.BaseApiRepo
import com.example.movieapp.shared.api.BaseRetrofitClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListRepo @Inject constructor(override var apiClient: BaseRetrofitClient) : MovieListReposing, BaseApiRepo {
    override suspend fun getTopRatedMovies(pageNumber: Int): Flow<List<Movie>> {
        return handleResponse(
            {
                apiClient.webService.getTopRatedMovies(BaseApiRepo.API_KEY, pageNumber)
            }
        ) {
            it.results
        }
    }

    override suspend fun getMostPopularMovies(pageNumber: Int): Flow<List<Movie>> {
        return handleResponse(
            {
                apiClient.webService.getMostPopularMovies(BaseApiRepo.API_KEY, pageNumber)
            }
        ) {
            it.results
        }
    }
}