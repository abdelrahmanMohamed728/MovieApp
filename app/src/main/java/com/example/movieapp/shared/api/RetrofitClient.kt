package com.example.movieapp.shared.api

import android.os.Build
import android.util.Log
import com.example.basemodule.basemodule.model.BaseError
import com.example.movieapp.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

abstract class RetrofitClient {
    val WebServiceTag = "Webservice"
    lateinit var webService: WebService
    var authToken: String? = null
    var versionName: String = ""
    var baseUrl: String = ""

    fun initWebService(): WebService {
        val logger = HttpLoggingInterceptor.Logger { message -> Log.d(WebServiceTag,message) }
        return retrofitBuilder(
            okHttpClient()
                .addInterceptor(
                    HttpLoggingInterceptor(logger)
                        .setLevel(
                            if (BuildConfig.DEBUG)
                                HttpLoggingInterceptor.Level.BODY
                            else
                                HttpLoggingInterceptor.Level.NONE
                        )
                )
                .build()
        )
    }

    private fun okHttpClient(): OkHttpClient.Builder {

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val ongoing: Request.Builder = chain.request().newBuilder()
                addHeaders(ongoing)
                chain.proceed(ongoing.build())
            }
    }

    private fun addHeaders(requestBuilder: Request.Builder) {
        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("App-Version", versionName)
        requestBuilder.addHeader("OS", "Android")
        requestBuilder.addHeader("OS-Version", "" + Build.VERSION.SDK_INT)
    }

    private fun buildGsonObject(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create()

    }

    private fun retrofitBuilder(httpOkHttpClient: OkHttpClient): WebService {
        val gson = buildGsonObject()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpOkHttpClient)
            .build()
            .create(WebService::class.java)
    }

    abstract fun parseError(errorBody: ResponseBody?) : BaseError

    fun updateToken(newToken : String){
        authToken = newToken
    }

    fun clearToken() {
        authToken = null
    }
}