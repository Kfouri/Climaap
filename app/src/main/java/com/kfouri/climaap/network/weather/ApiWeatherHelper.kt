package com.kfouri.climaap.network.weather

class ApiWeatherHelper(private val apiWeatherService: ApiWeatherService) {

    suspend fun getWeatherByLatLon(lat: Double, lon: Double) = apiWeatherService.getWeatherByLatLon(lat, lon)
}