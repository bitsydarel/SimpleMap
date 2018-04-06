package com.dbeginc.simplemap.presentation.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.dbeginc.simplemap.domain.ThreadProvider
import com.dbeginc.simplemap.domain.entities.Location
import com.dbeginc.simplemap.domain.entities.RequestState
import com.dbeginc.simplemap.domain.repository.LocationsRepository
import com.dbeginc.simplemap.presentation.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LocationsViewModel @Inject constructor(private val repo: LocationsRepository, private val threads: ThreadProvider): ViewModel() {
    private val subscriptions: CompositeDisposable = CompositeDisposable()
    private val requestState: MutableLiveData<RequestState> = MutableLiveData()
    private val _locations: MutableLiveData<List<Location>> = MutableLiveData()

    /**
     * Request state event
     * This method help me
     * to subscribe to the status
     * of any request made by viewModel
     * And also avoid memory leak :))
     */
    fun getRequestStateEvent() : LiveData<RequestState> = requestState

    fun getLocations() : LiveData<List<Location>> = _locations

    fun loadLocations() {
        repo.getAllLocations()
                .doOnSubscribe { requestState.postValue(RequestState.LOADING) }
                .doAfterNext { requestState.postValue(RequestState.COMPLETED) }
                .doOnError { requestState.postValue(RequestState.FAILED) }
                .observeOn(threads.UI)
                .subscribe(_locations::postValue, { Log.e("simplemap", it.localizedMessage, it) })
                .addTo(subscriptions)
    }


    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

}