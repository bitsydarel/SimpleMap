package com.dbeginc.simplemap.domain.entities

data class Location (
        val id: Long,
        val title: String,
        val description: String,
        val latitude: Double,
        val longitude: Double
)