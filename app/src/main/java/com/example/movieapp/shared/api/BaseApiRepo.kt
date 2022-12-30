package com.example.movieapp.shared.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.Response

interface BaseApiRepo : BaseReposing {
    suspend fun <R,M>  handleResponse(request: suspend () -> Response<R>, mapResponse: (R) -> M) : Flow<M> {
        return flow {
            val response = request()
            if (response.isSuccessful){
                val returningData : M? = response.body()?.let { mapResponse(it) }
                returningData?.let {
                    emit(it)
                    return@flow
                }
            } else {
                if (response.code() == 404){
                    // TODO this should be fixed ASAP
                    error(NotFoundThrowable.NOT_FOUND_MESSAGE)
                }else{
                    error(error(response.errorBody()))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun <R> handleEmptyResponse(request: suspend () -> Response<R>) : Flow<Boolean> {
        return flow {
            val response = request()
            if (response.isSuccessful){
                emit(true)
            } else {
                if (response.code() == 404) {
                    error(NotFoundThrowable.NOT_FOUND_MESSAGE)
                }else{
                    error(response.errorBody())
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun error(errorBody: ResponseBody?): String {
        return apiClient.parseError(errorBody).message
    }

    companion object{
        const val API_KEY = "e9fbc87a2d7ee2a8fe830d100ec07bb0"
    }

}

class NotFoundThrowable(message: String?) : Throwable(message){
    override fun getLocalizedMessage(): String? {
        return message
    }

    companion object{
        const val NOT_FOUND_MESSAGE = "NotFound"
    }
}