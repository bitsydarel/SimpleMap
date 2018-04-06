package com.dbeginc.simplemap.data.datasources.room

import android.content.Context
import com.dbeginc.simplemap.data.datasources.LocalLocationsDataSource
import com.dbeginc.simplemap.data.proxies.mapper.toDomain
import com.dbeginc.simplemap.data.proxies.mapper.toProxy
import com.dbeginc.simplemap.domain.entities.Location
import io.reactivex.Completable
import io.reactivex.Flowable

class RoomLocationsDataSource private constructor(private val db: RoomLocationsDatabase) : LocalLocationsDataSource {
    companion object {
        @JvmStatic fun create(context: Context) : RoomLocationsDataSource =
                RoomLocationsDataSource(db = RoomLocationsDatabase.create(context))
    }

    override fun getAllLocations(): Flowable<List<Location>> {
        return db.dao()
                .getAllLocations()
                .map { locations -> locations.map { it.toDomain() } }
    }

    override fun updateLocations(locations: List<Location>): Completable {
        return Completable.fromAction {
            db.dao().addLocations(locations = locations.map { it.toProxy() })
        }
    }
}