package com.kfouri.climaap.model

import com.google.gson.annotations.SerializedName

data class CurrentLocationResponse (
    @SerializedName("region_name")
    val regionName: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
)