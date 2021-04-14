package com.ratih.newsapp.network

import com.ratih.newsapp.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface
ApiService {
    @GET("top-headlines")
    fun getTopHeadlinesNews(
            @Query("country") country : String?,
            @Query("apikey") apikey : String?
    ): Call<ResponseNews>
}