package com.xm.question.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {

    private const val TAG = "Network"
    private val LEVEL = HttpLoggingInterceptor.Level.BASIC
    private const val REQUEST_TIME_OUT_INTERVAL = 10L //seconds
    private const val BASE_URL = "https://xm-assignment.web.app"

    private val gson: Gson = GsonBuilder().create()

    private val logger = HttpLoggingInterceptor.Logger {
        Log.i(TAG, it)
    }

    private val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
        level = LEVEL
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(REQUEST_TIME_OUT_INTERVAL, TimeUnit.SECONDS)
            .build()
    }

    private fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun xmClient(): Retrofit = retrofit(
        okHttpClient()
    )

}