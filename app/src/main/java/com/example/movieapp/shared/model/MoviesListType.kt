package com.example.movieapp.shared.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class MoviesListType(val type: String):Parcelable {
    MOST_POPULAR("MOST_POPULAR"),
    TOP_RATED("TOP_RATED");
    companion object{
        const val SORT_TYPE = "SORT_TYPE"
    }
}