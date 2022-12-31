package com.example.basemodule.basemodule.pagination

interface DataSourceFunctions<Key:Any,Data:Any> {

    abstract var currentPage:Key
    abstract var pageLength:Key

    abstract fun getNextPage(currentPage: Key, lastPage: Key): Key?

    abstract fun getPrevPage(currentPage: Key): Key?
}