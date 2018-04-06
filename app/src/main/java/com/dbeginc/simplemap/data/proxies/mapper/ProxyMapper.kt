package com.dbeginc.simplemap.data.proxies.mapper

import com.dbeginc.simplemap.data.proxies.local.LocalLocation
import com.dbeginc.simplemap.data.proxies.remote.RemoteLocation
import com.dbeginc.simplemap.domain.entities.Location


fun RemoteLocation.toDomain() : Location = Location(
        id = id,
        title = title,
        description = description,
        latitude = lat,
        longitude = lng
)

fun LocalLocation.toDomain() : Location = Location(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude
)

fun Location.toProxy() : LocalLocation = LocalLocation(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude
)