package com.dbeginc.simplemap.presentation.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dbeginc.simplemap.presentation.utils.SimpleMapViewModelFactory
import com.dbeginc.simplemap.presentation.viewmodels.LocationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds // Binds all dependencies needed
    @IntoMap // Add this viewModel into the map that will be passed the viewModelProvider
    @ViewModelKey(LocationsViewModel::class) // value of element as this
    abstract fun bindLocationsViewModel(locationsViewModel: LocationsViewModel): ViewModel

    @ApplicationScope
    @Binds
    abstract fun bindViewModelFactory(factory: SimpleMapViewModelFactory): ViewModelProvider.Factory

}