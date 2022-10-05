package com.example.dailynews.network

import com.example.dailynews.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitClient {
    private var retrofit:Retrofit? = null

    private fun getClient():Retrofit = retrofit ?: Retrofit.Builder().run {
        baseUrl(Constants.BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        build().also {
            retrofit = it
        }
    }

    val apiService: ApiService = getClient().create(ApiService::class.java)

}