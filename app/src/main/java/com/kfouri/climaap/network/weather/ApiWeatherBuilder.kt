package com.kfouri.climaap.network.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiWeatherBuilder {

    private const val BASE_URL = "http://api.openweathermap.org/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val apiWeatherService: ApiWeatherService = getRetrofit().create(ApiWeatherService::class.java)
}