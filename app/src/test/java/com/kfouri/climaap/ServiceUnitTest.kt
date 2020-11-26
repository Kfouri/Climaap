package com.kfouri.climaap

import com.kfouri.climaap.network.IpApiService
import com.kfouri.climaap.network.IpIfyService
import com.kfouri.climaap.network.weather.ApiWeatherBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ServiceUnitTest {

    private val weatherService by lazy {
        ApiWeatherBuilder.apiWeatherService
    }

    private val ipIfyService by lazy {
        IpIfyService.create()
    }

    private val ipApiService by lazy {
        IpApiService.create()
    }

    @Test
    internal fun shouldCallServiceWeather() {
        runBlocking {
            val result = weatherService.getWeatherByLatLon(1.0,1.0)
            println(result)
        }
    }

    @Test
    internal fun shouldCallServiceIpIfy() {
        runBlocking {
            val result = ipIfyService.getPublicIp()
            println(result.body())
        }
    }

    @Test
    internal fun shouldCallServiceIpApi() {
        runBlocking {
            val result = ipApiService.getCurrentLocation("0.0.0.0")
            println(result.body())
        }
    }

}