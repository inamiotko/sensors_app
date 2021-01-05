package com.example.projekt_przetwarzanie

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppDataService {
    @POST("save-localization")
    suspend fun createLocalization(
        @Body appDataDTO: AppDataDTO
    ): Response<Int>

    @POST("save-gyroscope")
    suspend fun createGyroscope(
        @Body appDataDTO: AppDataDTO
    ): Response<Int>

    @POST("save-accelerometer")
    suspend fun createAccelerometer(
        @Body appDataDTO: AppDataDTO
    ): Response<Int>

    @POST("save-light")
    suspend fun createLight(
        @Body appDataDTO: AppDataDTO
    ): Response<Int>

    @POST("save-proxy")
    suspend fun createProxy(
        @Body appDataDTO: AppDataDTO
    ): Response<Int>
}