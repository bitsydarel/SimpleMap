package com.dbeginc.simplemap

import android.content.Context
import com.dbeginc.simplemap.domain.repository.LocationsRepository
import com.dbeginc.simplemap.presentation.di.DataModule
import org.mockito.Mockito

open class MockDataModule : DataModule() {
    override fun provideLocationsRepository(appContext: Context) : LocationsRepository =
            Mockito.mock(LocationsRepository::class.java)
}