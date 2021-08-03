package com.example.android.testesolutis.api

import com.example.android.testesolutis.model.Extrato
import com.example.android.testesolutis.model.Login
import com.example.android.testesolutis.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Service {
    @POST("login")
    suspend fun login(@Body login: Login): Response<User>

    @GET("extrato")
    suspend fun extrato(@Header("token") token: String): Response<List<Extrato>>
}