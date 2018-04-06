package com.dbeginc.simplemap.presentation.di

import android.content.Context
import com.dbeginc.simplemap.data.RxThreadProvider
import com.dbeginc.simplemap.data.repositories.LocationsRepositoryImpl
import com.dbeginc.simplemap.domain.ThreadProvider
import com.dbeginc.simplemap.domain.repository.LocationsRepository
import dagger.Module
import dagger.Provides


@Module
open class DataModule {
    @Provides
    @ApplicationScope
    open fun provideLocationsRepository(appContext: Context) : LocationsRepository =
            LocationsRepositoryImpl.create(appContext)

    @Provides
    @ApplicationScope
    protected fun provideThreadProvider() : ThreadProvider = RxThreadProvider

}