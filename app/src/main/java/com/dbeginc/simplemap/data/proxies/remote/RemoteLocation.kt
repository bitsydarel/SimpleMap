package com.dbeginc.simplemap.data.proxies.remote

import com.google.gson.annotations.SerializedName

data class RemoteLocation(@SerializedName("lng")
                          val lng: Double,
                          @SerializedName("description")
                          val description: String,
                          @SerializedName("id")
                          val id: Long,
                          @SerializedName("title")
                          val title: String,
                          @SerializedName("lat")
                          val lat: Double
)