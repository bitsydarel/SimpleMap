package com.dbeginc.simplemap.data.datasources.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.dbeginc.simplemap.data.proxies.local.LocalLocation

@Database(entities = [LocalLocation::class], version = 1, exportSchema = false)
abstract class RoomLocationsDatabase : RoomDatabase() {
    abstract fun dao() : RoomLocationsDao

    companion object {
        @JvmStatic fun create(context: Context) : RoomLocationsDatabase {
            return Room.databaseBuilder(
                    context,
                    RoomLocationsDatabase::class.java,
                    "locations_db" /* Hard coded because it's a simple demo project */
            ).build()
        }
    }
}