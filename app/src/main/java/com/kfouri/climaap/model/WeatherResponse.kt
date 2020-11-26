package com.kfouri.climaap.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: List<Daily>,
)

data class Current(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
)


data class Weather (
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)

data class Daily (
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("pop")
    val pop: Float,
    @SerializedName("rain")
    val rain: Float,
    @SerializedName("uvi")
    val uvi: Float,
    )

data class Temp (
    @SerializedName("day")
    val day: Float,
    @SerializedName("min")
    val min: Float,
    @SerializedName("max")
    val max: Float,
)

data class FeelsLike (
    @SerializedName("day")
    val day: Float,
    @SerializedName("night")
    val night: Float,
)