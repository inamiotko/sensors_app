package com.example.projekt_przetwarzanie

data class AppDataDTO (
    val latitude: String? = null,
    val longitude: String? = null,
    val accelerometerX: Double? = null,
    val accelerometerY: Double? = null,
    val accelerometerZ: Double? = null,
    val gyroscopeX: Double? = null,
    val gyroscopeY: Double? = null,
    val gyroscopeZ: Double? = null,
    val lightValue: Double? = null,
    val proxy_value: Double? = null
)