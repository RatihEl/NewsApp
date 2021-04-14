package com.ratih.newsapp.network

import com.google.gson.GsonBuilder
import com.ratih.newsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//okhttp itu widget jadi butuh builder
//setleniet untun settime
//gson untuk mengenteri data
object RetrofitConfig {
    //httploginginterceptor buat koneksi atau membawa atau memmangil data
    val interceptor= HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    //untuk menghubungkan ke clientnya
    val client= OkHttpClient.Builder().addInterceptor(interceptor)
        .retryOnConnectionFailure(true)
            //ketika prosesnya lebih dari 30 detik, berarti datanya gagal bisa karena ga ada
            //koneksi  internet
        .connectTimeout(30,TimeUnit.SECONDS)
        .build()

    //untuk convert menjadi gson
    val gson = GsonBuilder().setLenient().create()

    //entery data
    //buat ngatur url mana yang mau di eksekusi
    val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(client)
            //buat nge convert kedalam gson
        .addConverterFactory(GsonConverterFactory.create(gson)).build()

    //function untuk menyambung end point dari class api service
    fun getInstance() : ApiService = retrofit.create(ApiService :: class.java)

}