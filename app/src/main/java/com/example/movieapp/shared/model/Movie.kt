package com.example.movieapp.shared.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
): Parcelable