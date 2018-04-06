package com.dbeginc.simplemap.data.datasources

import com.dbeginc.simplemap.domain.entities.Location
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * LocalLocationsDataSource represent a source of data that can be memory or offline
 * every data source that are kept on device must implementation this interface
 *
 * This allow us to easily switch sources without changing most of our code
 */
interface LocalLocationsDataSource {
    fun getAllLocations() : Flowable<List<Location>>

    fun updateLocations(locations: List<Location>) : Completable
}