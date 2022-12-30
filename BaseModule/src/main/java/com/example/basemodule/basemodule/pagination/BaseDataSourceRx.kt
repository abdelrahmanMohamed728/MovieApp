package com.example.basemodule.basemodule.pagination

import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


abstract class BaseDataSourceRx<Key:Any,Data:Any> : RxPagingSource<Key, Data>(),
    DataSourceFunctions<Key, Data> {

    abstract fun requestData(currentPage: Key): Single<Any>
    abstract fun parseData(response: Any): List<Data>
    abstract fun getLastPage(response: Any, currentPage: Key? = null): Key

    override fun loadSingle(params: LoadParams<Key>): Single<LoadResult<Key, Data>> {
        val position = params.key ?: currentPage

        return requestData(position)
            .subscribeOn(Schedulers.io())
            .map {
                val lastPage = getLastPage(it,position)
                val prevPage = getPrevPage(currentPage)
                val nextPage = getNextPage(position,lastPage)
                val data  = parseData(it)
                toLoadResult(data,prevPage,nextPage)

            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(data: List<Data>,prevPage:Key?,nextPage:Key?): LoadResult<Key, Data> {
        return LoadResult.Page(
            data = data,
            prevKey = prevPage,
            nextKey = nextPage
        )
    }
}