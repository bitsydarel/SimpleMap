package com.dbeginc.simplemap.domain.repository

import com.dbeginc.simplemap.domain.entities.Location
import io.reactivex.Flowable

interface LocationsRepository {
    fun getAllLocations() : Flowable<List<Location>>
}