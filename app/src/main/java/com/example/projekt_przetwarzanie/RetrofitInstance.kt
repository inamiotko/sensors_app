package com.example.projekt_przetwarzanie

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    const val BASE_URL = "http://192.168.0.31:8085/serverproz/"
    // tworzy interfejs z api
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AppDataService by lazy {
        retrofit.create(AppDataService::class.java)
    }
}