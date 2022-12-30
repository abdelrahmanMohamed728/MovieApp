package com.example.basemodule.basemodule

import com.example.drdbasemodule.model.Action

interface InitFragment {
    fun initObservers()
    fun initRecycler()
    fun initListeners()
    fun doAction(action: Action)
    fun swipeToRefreshListener()
    fun addShimmerEffect(it: String)
    fun hideShimmerEffect(it: String)
}