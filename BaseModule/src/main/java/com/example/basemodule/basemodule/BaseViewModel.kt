package com.example.basemodule.basemodule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.basemodule.basemodule.BaseFragment
import com.example.drdbasemodule.model.Action

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var errorLiveData = MutableLiveData<Throwable>()
    var actionLiveData = MutableLiveData<Action>()
    var authErrorLiveData = MutableLiveData<Throwable>()
    var addShimmerLiveData = MutableLiveData<String>()
    var hideShimmerLiveData = MutableLiveData<String>()

    fun addAction(action: Action){
        actionLiveData.postValue(action)
    }

    fun handleNetworkError(throwable : Throwable) {
        errorLiveData.postValue(throwable)
    }

    fun handleAuthNetworkError(throwable : Throwable) {
        authErrorLiveData.postValue(throwable)
    }

    fun addLoadingObject(){
        var action = Action()
        action.actionsString = BaseFragment.SHOW_LOADING
        actionLiveData.postValue(action)
    }

    fun removeLoadingObject(){
        var action = Action()
        action.actionsString = BaseFragment.HIDE_LOADING
        actionLiveData.postValue(action)
    }

    fun addShimmerLoading(shimmerIdentifier: String){
        addShimmerLiveData.value = shimmerIdentifier
    }

    fun hideShimmerLoading(shimmerIdentifier: String){
        hideShimmerLiveData.value = shimmerIdentifier
    }
}