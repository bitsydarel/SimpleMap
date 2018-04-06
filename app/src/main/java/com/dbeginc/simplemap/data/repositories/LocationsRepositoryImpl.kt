package com.dbeginc.simplemap.data.repositories

import android.content.Context
import android.util.Log
import com.dbeginc.simplemap.data.RxThreadProvider
import com.dbeginc.simplemap.data.datasources.LocalLocationsDataSource
import com.dbeginc.simplemap.data.datasources.RemoteLocationsDataSource
import com.dbeginc.simplemap.data.datasources.httpapi.HttpApiDataSource
import com.dbeginc.simplemap.data.datasources.room.RoomLocationsDataSource
import com.dbeginc.simplemap.domain.ThreadProvider
import com.dbeginc.simplemap.domain.entities.Location
import com.dbeginc.simplemap.domain.repository.LocationsRepository
import io.reactivex.Flowable
import io.reactivex.observers.DisposableCompletableObserver

class LocationsRepositoryImpl private constructor(
        private val local: LocalLocationsDataSource,
        private val remote: RemoteLocationsDataSource,
        private val threads: ThreadProvider
) : LocationsRepository {
    companion object {
        @JvmStatic
        fun create(context: Context) : LocationsRepository {
            val httpApiDataSource = HttpApiDataSource.create(context)
            val roomLocationsDataSource = RoomLocationsDataSource.create(context)

            return LocationsRepositoryImpl (
                    local = roomLocationsDataSource,
                    remote = httpApiDataSource,
                    threads = RxThreadProvider
            )
        }
    }

    override fun getAllLocations(): Flowable<List<Location>> {
        return local.getAllLocations()
                .subscribeOn(threads.CP)
                .doOnSubscribe {
                    // Fetch from network and update database
                    // Which trigger room reactive stream on next with the new value
                    remote.getAllLocations()
                            .subscribeOn(threads.IO)
                            .flatMapCompletable { freshData ->
                                local.updateLocations(freshData)
                                        .subscribeOn(threads.CP)
                            }
                            .subscribe(UpdateObserver())
                }
    }

    private inner class UpdateObserver : DisposableCompletableObserver() {
        override fun onComplete() {
            // If it production than we should not log information message like that
            Log.i("simpleweather", "data updated")
        }

        override fun onError(e: Throwable) {
            // If it production than we should not log error message like that
            // should use something as crash reporter (example: Crashlytics)
            Log.e("simpleweather", e.localizedMessage, e)
        }
    }
}