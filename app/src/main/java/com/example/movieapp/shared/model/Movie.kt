package com.example.movieapp.shared.model

import com.google.gson.annotations.SerializedName

class Movie(
    @SerializedName("poster_path")
    val imagePath: String?,
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val userRating: Double,
    @SerializedName("original_title")
    val title: String?
)