package com.dbeginc.simplemap.data.datasources.httpapi

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.dbeginc.simplemap.data.datasources.RemoteLocationsDataSource
import com.dbeginc.simplemap.data.proxies.mapper.toDomain
import com.dbeginc.simplemap.data.proxies.remote.RemoteLocation
import com.dbeginc.simplemap.domain.entities.Location
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.jetbrains.annotations.TestOnly
import java.io.File
import java.util.concurrent.TimeUnit

class HttpApiDataSource private constructor(private val httpApiUrl: String): RemoteLocationsDataSource {

    companion object {
        private const val NETWORK_CACHE : Long = 50 * 1024 * 1024 // 50 MB

        @JvmStatic fun create(context: Context) : HttpApiDataSource {
            val appContext : Context = context.applicationContext

            val client = OkHttpClient.Builder()
                    .connectTimeout(35, TimeUnit.SECONDS)
                    .writeTimeout(35, TimeUnit.SECONDS)
                    .readTimeout(55, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .cache(Cache(
                            File(appContext.cacheDir, "http_location_api_cache"),
                            NETWORK_CACHE
                    ))
                    .build()

            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)

            AndroidNetworking.initialize(context, client)

            return HttpApiDataSource(httpApiUrl = "http://www.json-generator.com/api/json/get/bPvorlFkwO?indent=2")
        }

        @TestOnly
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        @JvmStatic
        fun create(url: String) : HttpApiDataSource = HttpApiDataSource(httpApiUrl = url)

    }

    override fun getAllLocations(): Single<List<Location>> {
        return Rx2AndroidNetworking
                .get(httpApiUrl)
                .build()
                .getObjectListSingle(RemoteLocation::class.java)
                .map { locations -> locations.map { it.toDomain() } }
    }
}