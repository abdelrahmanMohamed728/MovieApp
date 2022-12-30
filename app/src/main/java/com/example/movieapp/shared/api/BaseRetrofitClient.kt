package com.example.movieapp.shared.api

import com.example.basemodule.basemodule.model.BaseError
import com.google.gson.Gson
import okhttp3.ResponseBody

class BaseRetrofitClient : RetrofitClient() {

    companion object {
        const val WebServiceTag = "Webservice"
        const val PAGE_COUNT = 10
        const val DEFAULT_TOKEN = "none"
        private lateinit var instance: BaseRetrofitClient

        private fun init(
            currentToken: String?,
            baseUrl: String
        ) {
            instance = BaseRetrofitClient()
            instance.baseUrl = baseUrl
            instance.authToken = currentToken
            instance.webService = instance.initWebService()
        }

        fun getInstance(
            currentToken: String?,
            baseUrl: String
        ): BaseRetrofitClient {
            if (!this::instance.isInitialized) {
                init(currentToken, baseUrl)
            }
            return instance
        }

        fun getInstance(): BaseRetrofitClient {
            return instance
        }
    }

    override fun parseError(errorBody: ResponseBody?): BaseError {
        return try{
            if (errorBody != null) {
                Gson().fromJson(errorBody.string(), BaseError::class.java)
            } else {
                BaseError("")
            }
        } catch (e: Exception){
            BaseError(e.localizedMessage.toString())
        }
    }

}