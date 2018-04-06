package com.dbeginc.simplemap

import android.os.StrictMode
import com.dbeginc.simplemap.presentation.MainActivity
import com.dbeginc.simplemap.presentation.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SimpleMap : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            StrictMode.noteSlowCall(packageName) // Detect slow call made on UI Thread

            StrictMode.setThreadPolicy(
                    StrictMode.ThreadPolicy.Builder()
                            .detectAll() // Detect any thread overuse or work on main thread ect..
                            .penaltyLog() // Log it in log
                            .build()
            )

            StrictMode.setVmPolicy(
                    StrictMode.VmPolicy.Builder()
                            /*
                             * Limit instance of activity to two,
                             * sometime android framework create two classes on rotation the previous one
                             * and the new one
                             */
                            .setClassInstanceLimit(MainActivity::class.java, 2)
                            .detectAll()
                            .penaltyLog()
                            .build()
            )
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder().create(this)
}