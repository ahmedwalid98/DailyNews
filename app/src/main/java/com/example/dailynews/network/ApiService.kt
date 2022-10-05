package com.example.dailynews.network

import com.example.dailynews.models.NewsResponse
import com.example.dailynews.models.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
    ): SourcesResponse

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String,
        @Query("sources") source: String
    ):NewsResponse

    @GET("v2/everything")
    fun searchInNews(
        @Query("apiKey") apiKey: String,
        @Query("q") keyword: String,
        @Query("sources") source: String
    ): NewsResponse
}