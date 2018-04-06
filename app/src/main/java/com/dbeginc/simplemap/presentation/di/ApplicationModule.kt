package com.dbeginc.simplemap.presentation.di

import android.content.Context
import com.dbeginc.simplemap.presentation.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerApplication

@Module
abstract class ApplicationModule {
    @ApplicationScope
    @Binds
    abstract fun provideApplicationContext(application: DaggerApplication): Context

    @ContributesAndroidInjector
    abstract fun provideMainActivity() : MainActivity
}