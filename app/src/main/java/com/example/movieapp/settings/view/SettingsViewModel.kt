package com.example.movieapp.settings.view

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.basemodule.basemodule.BaseViewModel
import com.example.movieapp.shared.SharedPrefHelper
import com.example.movieapp.shared.model.MoviesListType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    val sortTypeLiveData = MutableLiveData<MoviesListType>()

    fun getSortType(context: Context){
        val type = SharedPrefHelper.getSortType(context)
        sortTypeLiveData.value = MoviesListType.valueOf(type)
    }

    fun updateSortType(context: Context,type: MoviesListType) {
        SharedPrefHelper.updateSortType(context,type)
    }


}