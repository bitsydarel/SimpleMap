package com.dbeginc.simplemap.data.datasources.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dbeginc.simplemap.data.proxies.local.LocalLocation
import io.reactivex.Flowable

@Dao
interface RoomLocationsDao {
    @Query("SELECT * FROM locations")
    fun getAllLocations() : Flowable<List<LocalLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocations(locations: List<LocalLocation>)
}