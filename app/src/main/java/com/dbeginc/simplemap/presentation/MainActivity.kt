package com.dbeginc.simplemap.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.dbeginc.simplemap.R
import com.dbeginc.simplemap.databinding.ActivityMainBinding
import com.dbeginc.simplemap.domain.entities.Location
import com.dbeginc.simplemap.domain.entities.RequestState
import com.dbeginc.simplemap.presentation.utils.isInternetAvailable
import com.dbeginc.simplemap.presentation.viewmodels.LocationsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    @Inject lateinit var factory: Lazy<ViewModelProvider.Factory>
    private lateinit var binding: ActivityMainBinding
    private var locations: List<Location> = emptyList()
    private var maps: GoogleMap? = null

    private val viewModel: LocationsViewModel by lazy {
        ViewModelProviders.of(this, factory.get())[LocationsViewModel::class.java]
    }

    private val stateObserver = Observer<RequestState> {
        onStateChanged(state = it!!)
    }

    private val locationsObserver = Observer<List<Location>> {
        if (locations.isNotEmpty()) maps?.clear()

        locations = it!!

        fillMap()
    }

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.mainToolbar)

        viewModel.getRequestStateEvent().observe(this, stateObserver)

        viewModel.getLocations().observe(this, locationsObserver)

        if (savedState == null) retrieveLocations()

    }

    override fun onStart() {
        super.onStart()

        (supportFragmentManager.findFragmentById(R.id.locations) as? SupportMapFragment)?.getMapAsync(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.action_refresh) {
            retrieveLocations()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap

        googleMap.setContentDescription("Simple Locations Map")

        googleMap.uiSettings.isMapToolbarEnabled = false // Disable unnecessary google maps feature

        googleMap.uiSettings.setAllGesturesEnabled(true)

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        googleMap.setOnMarkerClickListener(this)

        fillMap()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        locations.find {
            it.latitude == marker.position.latitude
                    && it.longitude == marker.position.longitude

        }?.let {
            Snackbar.make(binding.mainLayout, it.description, Snackbar.LENGTH_LONG)
                    .show()
        }

        return true
    }

    private fun onStateChanged(state: RequestState) {
        when(state) {
            RequestState.LOADING -> return // Since it's demo not interested
            RequestState.COMPLETED -> return // Since it's demo not interested
            RequestState.FAILED -> onRequestFailed()
        }
    }

    private fun retrieveLocations() {
        if (isInternetAvailable()) viewModel.loadLocations()
        else onInternetNotAvailable()
    }

    private fun fillMap() {
        // Lazily loaded element
        locations.asSequence().forEach { (_, title, _, latitude, longitude) ->
            val latWithLong = LatLng(latitude, longitude)

            val markerOptions = MarkerOptions().position(latWithLong).title(title)

            maps?.addMarker(markerOptions)
        }
    }

    private fun onInternetNotAvailable() {
        Snackbar.make(binding.mainLayout, R.string.no_internet_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.close) {}
                .show()
    }

    private fun onRequestFailed() {
        Snackbar.make(binding.mainLayout, R.string.could_not_load_locations, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry) { viewModel.loadLocations() }
                .show()
    }
}
