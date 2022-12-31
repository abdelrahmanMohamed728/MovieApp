package com.example.movieapp.movieslist.model.repo

import androidx.paging.PagingState
import com.example.basemodule.basemodule.pagination.BaseDataSource
import com.example.movieapp.shared.model.Movie
import com.example.movieapp.shared.model.MoviesListType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class MoviesListDataSource(
    override var currentPage: Int,
    override var pageLength: Int,
    private val repo: MovieListReposing,
    private val type: MoviesListType
) : BaseDataSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun requestData(currentPage: Int): Any {
        return if (type == MoviesListType.MOST_POPULAR) {
            repo.getMostPopularMovies(currentPage)
        } else{
            repo.getTopRatedMovies(currentPage)
        }
    }

    override fun getNextPage(currentPage: Int, lastPage: Int): Int? {
        return if (currentPage < lastPage) currentPage + 1 else null
    }

    override fun getPrevPage(currentPage: Int): Int? {
        return if (currentPage == 1){
            null
        }else{
            currentPage - 1
        }
    }

    override suspend fun getLastPage(response: Any, currentPage: Int?): Int {
        val getData = parseData(response)
        return if (getData.isEmpty()){
            0
        }else{
            currentPage?.plus(2) ?: 0
        }
    }

    override suspend fun parseData(response: Any): List<Movie> {
        var movieList: List<Movie> = listOf()
        (response as Flow<List<Movie>>).collectLatest {
            movieList = it
        }
        return movieList
    }
}