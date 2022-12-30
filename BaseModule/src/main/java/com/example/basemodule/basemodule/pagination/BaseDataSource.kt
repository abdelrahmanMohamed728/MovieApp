package com.example.basemodule.basemodule.pagination

import androidx.paging.PagingSource


abstract class BaseDataSource<Key:Any,Data:Any> : PagingSource<Key, Data>(),
    DataSourceFunctions<Key, Data> {

    abstract suspend fun requestData(currentPage: Key): Any

    abstract suspend fun parseData(response: Any): List<Data>

    abstract suspend fun getLastPage(response: Any, currentPage: Key? = null): Key

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Data> {
        return try {

            val currentPage = params.key ?: currentPage
            val response = requestData(currentPage)
            val data = parseData(response)
            val lastPage = getLastPage(response,currentPage)
            val prevKey = getPrevPage(currentPage)
            val nextKey = getNextPage(currentPage,lastPage)
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}