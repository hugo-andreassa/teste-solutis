package com.example.android.testesolutis.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SolutisApi {

    private const val BASE_URL: String = "https://api.mobile.test.solutis.xyz/"

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: Service = retrofit.create(Service::class.java)
}