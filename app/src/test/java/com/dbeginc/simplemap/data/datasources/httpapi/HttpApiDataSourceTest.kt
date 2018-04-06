package com.dbeginc.simplemap.data.datasources.httpapi

import com.dbeginc.simplemap.getFileAsStringJVM
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class HttpApiDataSourceTest {
    private lateinit var server: MockWebServer
    private lateinit var httpApiDataSource: HttpApiDataSource

    @Before
    fun setUp() {
        val mockServer = MockWebServer()

        mockServer.start()

        httpApiDataSource = HttpApiDataSource.create(mockServer.url("/").toString())

        server = mockServer
    }

    @Test
    fun getAllLocations() {
        val serverResponse = MockResponse()

        serverResponse.apply {
            setResponseCode(200)
            setBody(getFileAsStringJVM("locations.json"))
        }

        server.enqueue(serverResponse)

        httpApiDataSource.getAllLocations()
                .test()
                .assertValue { it.size == 100 }
                .assertValue { it.all { (it.latitude > 0 || it.latitude < 0) && (it.longitude > 0 || it.longitude < 0) } }
                .assertNoErrors()
    }
}