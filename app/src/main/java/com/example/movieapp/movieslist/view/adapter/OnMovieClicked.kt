package com.example.movieapp.movieslist.view.adapter

import com.example.movieapp.shared.model.Movie

interface OnMovieClicked {
    fun onMovieClicked(movie:Movie)
}