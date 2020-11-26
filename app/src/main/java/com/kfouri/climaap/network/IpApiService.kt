package com.kfouri.climaap.network

import com.kfouri.climaap.model.CurrentLocationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface IpApiService {

    @GET("{ip}?access_key=1a82c5f17399d4f909c4aefdf0f38a53")
    suspend fun getCurrentLocation(@Path("ip") ip: String): Response<CurrentLocationResponse>

    companion object {
        fun create(): IpApiService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.ipapi.com/")
                    .build()

            return retrofit.create(IpApiService::class.java)
        }
    }
}