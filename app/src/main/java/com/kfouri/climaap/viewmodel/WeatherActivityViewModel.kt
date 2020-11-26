package com.kfouri.climaap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kfouri.climaap.network.weather.ApiWeatherRepository
import com.kfouri.climaap.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class WeatherActivityViewModel(private val apiWeatherRepository: ApiWeatherRepository) : ViewModel() {

    fun getWeatherByLatLon(lat: Double, lon: Double) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiWeatherRepository.getWeatherByLatLon(lat, lon)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error!!!"))
        }
    }
}