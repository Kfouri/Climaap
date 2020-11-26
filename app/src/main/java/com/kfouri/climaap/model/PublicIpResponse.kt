package com.kfouri.climaap.model

import com.google.gson.annotations.SerializedName

data class PublicIpResponse (
    @SerializedName("ip")
    val ip: String
)