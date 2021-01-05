package com.example.projekt_przetwarzanie

import retrofit2.Response

class AppDataRepository {
    //funkcja tworzy i wysyła dane lokalizacji na serwer
    suspend fun createLocalization(appDataDTO: AppDataDTO): Response<Int> {
        return RetrofitInstance.api.createLocalization(appDataDTO)
    }
    //funkcja tworzy i wysyła dane z żyroskopu na serwer
    suspend fun createGyroscope(appDataDTO: AppDataDTO): Response<Int> {
        return RetrofitInstance.api.createGyroscope(appDataDTO)
    }
    //funkcja tworzy i wysyła dane z akcelerometru na serwer
    suspend fun createAccelerometer(appDataDTO: AppDataDTO): Response<Int> {
        return RetrofitInstance.api.createAccelerometer(appDataDTO)
    }
    //funkcja tworzy i wysyła dane z czujnika światła na serwer
    suspend fun createLight(appDataDTO: AppDataDTO): Response<Int> {
        return RetrofitInstance.api.createLight(appDataDTO)
    }
    //funkcja tworzy i wysyła dane z czujnika bliży na serwer
    suspend fun createProxy(appDataDTO: AppDataDTO): Response<Int> {
        return RetrofitInstance.api.createProxy(appDataDTO)
    }

}