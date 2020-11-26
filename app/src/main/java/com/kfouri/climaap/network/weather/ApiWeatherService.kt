package com.kfouri.climaap.network.weather

import com.kfouri.climaap.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeatherService {
    @GET("data/2.5/onecall?appid=b24da0714f4222971ec68976bbb65cf1&lang=en&units=metric")
    suspend fun getWeatherByLatLon(@Query("lat") lat: Double, @Query("lon") lon: Double): WeatherResponse
}