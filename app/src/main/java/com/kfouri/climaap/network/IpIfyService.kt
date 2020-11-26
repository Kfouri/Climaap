package com.kfouri.climaap.network

import com.kfouri.climaap.model.PublicIpResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IpIfyService {

    @GET("?format=json")
    suspend fun getPublicIp(): Response<PublicIpResponse>

    companion object {
        fun create(): IpIfyService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.ipify.org/")
                    .build()

            return retrofit.create(IpIfyService::class.java)
        }
    }
}