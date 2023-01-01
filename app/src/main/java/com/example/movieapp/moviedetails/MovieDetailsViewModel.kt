package com.example.movieapp.moviedetails

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.basemodule.basemodule.BaseViewModel
import com.example.movieapp.moviedetails.MovieDetailsFragment.Companion.MOVIE_ITEM
import com.example.movieapp.shared.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    private val _movieLiveData = MutableLiveData<Movie>()

    fun getMovieLiveData() = _movieLiveData

    fun extractArguments(arguments: Bundle?) {
        if (arguments != null) {
            if (arguments.containsKey(MOVIE_ITEM)) {
                _movieLiveData.value = arguments.getParcelable(MOVIE_ITEM)
            }
        }
    }

}