package com.kfouri.climaap

import com.kfouri.climaap.network.IpApiService
import com.kfouri.climaap.network.IpIfyService
import com.kfouri.climaap.network.weather.ApiWeatherBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DataUnitTest {
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
    internal fun shouldCallGetWeatherByLatLon() {
        runBlocking {
            val lat = 1.0
            val lon = 1.0

            val result = weatherService.getWeatherByLatLon(lat,lon)
            println(result.lat.toString() + " - " + result.lon.toString())

            assert(result.lat == lat)
            assert(result.lon == lon)
        }
    }

    @Test
    internal fun shouldCallGetCurrentLocation() {
        runBlocking {
            val result = ipApiService.getCurrentLocation("200.126.185.17")
            assert(result.body()!!.regionName == "Cordoba")
        }
    }
}