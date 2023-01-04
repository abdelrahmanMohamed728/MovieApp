package com.example.movieapp.shared

import android.R.attr.password
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.movieapp.shared.model.MoviesListType
import com.example.movieapp.shared.model.MoviesListType.Companion.SORT_TYPE


class SharedPrefHelper {
    companion object{

        fun getSortType(context: Context): String{
            return getSharedPrefData(context, SORT_TYPE)?: MoviesListType.MOST_POPULAR.type
        }

        fun updateSortType(context: Context,value: MoviesListType){
            updateSharedPrefData(context, SORT_TYPE,value.type)
        }

        private fun getSharedPrefData(context: Context,key: String): String?{
            val prefs: SharedPreferences = context.getSharedPreferences("MovieApp",MODE_PRIVATE)
            return prefs.getString(key, null)
        }

        private fun updateSharedPrefData(context: Context,key: String, value: String){
            val preferences: SharedPreferences =
                context.getSharedPreferences("MovieApp", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }
}