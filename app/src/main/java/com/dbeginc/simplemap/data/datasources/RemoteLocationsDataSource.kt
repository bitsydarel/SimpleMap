package com.dbeginc.simplemap.data.datasources

import com.dbeginc.simplemap.domain.entities.Location
import io.reactivex.Single

/**
 * RemoteLocationsDataSource represent a source of data from network
 * every data source connected to network must implementation this interface
 *
 * This allow us to easily switch sources in the future without changing most of our code
 * and it's more testable
 */
interface RemoteLocationsDataSource {
    fun getAllLocations() : Single<List<Location>>
}