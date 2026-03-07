package com.szyzu.downbalt.services

import com.szyzu.downbalt.data.CobaltRequestBody
import com.szyzu.downbalt.data.CobaltResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("/")
    suspend fun postLink(@Body body: CobaltRequestBody): CobaltResponse
}