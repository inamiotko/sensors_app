package com.example.projekt_przetwarzanie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    private val appDataRepository = AppDataRepository()

   // funkcja tworzy i wysyła dane lokalizacji na serwer
    fun createLocalization(appDataDTO: AppDataDTO) {
        viewModelScope.launch {
            appDataRepository.createLocalization(appDataDTO)
        }
    }
    // funkcja tworzy i wysyła dane z żyroskopu na serwer
    fun createGyroscope(appDataDTO: AppDataDTO) {
        viewModelScope.launch {
            appDataRepository.createGyroscope(appDataDTO)
        }
    }
    // funkcja tworzy i wysyła dane z akcelerometru na serwer
    fun createAccelerometer(appDataDTO: AppDataDTO) {
        viewModelScope.launch {
            appDataRepository.createAccelerometer(appDataDTO)
        }
    }
    // funkcja tworzy i wysyła dane z czujnika światła na serwer
    fun createLight(appDataDTO: AppDataDTO) {
        viewModelScope.launch {
            appDataRepository.createLight(appDataDTO)
        }
    }
    // funkcja tworzy i wysyła dane z czujnika bliży na serwer
    fun createProxy(appDataDTO: AppDataDTO) {
        viewModelScope.launch {
            appDataRepository.createProxy(appDataDTO)
        }
    }
}