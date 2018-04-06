package com.dbeginc.simplemap.presentation.utils

import android.content.Context
import android.icu.util.ValueIterator
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Context.isInternetAvailable() : Boolean {
    val connectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?

    return connectionManager?.activeNetworkInfo?.isConnected == true
}

fun Disposable.addTo(taskContainer: CompositeDisposable) {
    taskContainer.add(this)
}