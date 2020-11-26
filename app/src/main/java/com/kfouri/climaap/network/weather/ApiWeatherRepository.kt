package com.kfouri.climaap.network.weather

class ApiWeatherRepository(private val apiWeatherHelper: ApiWeatherHelper) {
    suspend fun getWeatherByLatLon(lat: Double, lon: Double) = apiWeatherHelper.getWeatherByLatLon(lat, lon)
}