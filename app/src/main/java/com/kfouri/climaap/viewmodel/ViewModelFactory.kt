package com.kfouri.climaap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kfouri.climaap.network.weather.ApiWeatherHelper
import com.kfouri.climaap.network.weather.ApiWeatherRepository

class ViewModelFactory(private val apiWeatherHelper: ApiWeatherHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherActivityViewModel::class.java)) {
            return WeatherActivityViewModel(ApiWeatherRepository(apiWeatherHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}