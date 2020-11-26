package com.kfouri.climaap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kfouri.climaap.network.IpApiService
import com.kfouri.climaap.network.IpIfyService
import com.kfouri.climaap.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CitiesListActivityViewModel() : ViewModel() {

    private val ipApiService by lazy {
        IpApiService.create()
    }

    private val ipIfyService by lazy {
        IpIfyService.create()
    }

    fun getCurrentLocation(ip: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = ipApiService.getCurrentLocation(ip)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error!!!"))
        }
    }

    fun getPublicIP() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = ipIfyService.getPublicIp()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error!!!"))
        }
    }
}