package com.dbeginc.simplemap.data.proxies.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/* Hard coding the table name because it's just a demo project */
@Entity(tableName = "locations")
data class LocalLocation (
        @PrimaryKey val id: Long,
        val title: String,
        val description: String,
        val latitude: Double,
        val longitude: Double
)