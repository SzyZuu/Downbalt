package com.szyzu.downbalt.data

import com.szyzu.downbalt.services.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var baseUrl = ""

    fun setLink(link: String){
        baseUrl = link
    }

    val apiService: ApiService by lazy{
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}