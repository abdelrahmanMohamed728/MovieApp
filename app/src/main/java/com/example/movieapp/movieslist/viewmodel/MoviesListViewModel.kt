package com.example.movieapp.movieslist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.basemodule.basemodule.BaseViewModel
import com.example.movieapp.movieslist.model.repo.MovieListReposing
import com.example.movieapp.movieslist.model.repo.MoviesListDataSource
import com.example.movieapp.shared.model.Movie
import com.example.movieapp.shared.model.MoviesListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    application: Application,
    var repo: MovieListReposing
) : BaseViewModel(application) {

    private var _moviesLiveData = MutableLiveData<PagingData<Movie>>()
    fun getMoviesLiveData() = _moviesLiveData

    fun getMovies() {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = 20, prefetchDistance = 20)) {
                MoviesListDataSource(
                    1, 20,repo, MoviesListType.MOST_POPULAR
                )
            }.flow.cachedIn(viewModelScope)
                .catch {
                    handleNetworkError(it)
                }.collect {
                    _moviesLiveData.postValue(it)
                }
        }
    }

}