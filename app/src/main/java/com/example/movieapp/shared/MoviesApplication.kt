package com.example.movieapp.shared

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication: Application() {
    companion object {
        private var context: Context? = null
        fun getAppContext(): Context? {
            return MoviesApplication.context
        }
    }

    override fun onCreate() {
        super.onCreate()
        MoviesApplication.context = applicationContext
    }

}