package com.dbeginc.simplemap.presentation.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@ApplicationScope
@Component(modules = [
    ApplicationModule::class, DataModule::class, ViewModelModule::class,
    AndroidInjectionModule::class, AndroidSupportInjectionModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {
    @dagger.Component.Builder
    abstract class Builder : AndroidInjector.Builder<DaggerApplication>() {
        /* this method is neccessary for DaggerMock when running instrumentations tests */
        abstract fun dataModule(dataModule: DataModule): Builder
    }
}
