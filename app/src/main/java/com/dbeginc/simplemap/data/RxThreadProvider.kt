package com.dbeginc.simplemap.data

import com.dbeginc.simplemap.domain.ThreadProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxThreadProvider : ThreadProvider{
    override val UI: Scheduler = AndroidSchedulers.mainThread()
    override val CP: Scheduler = Schedulers.computation()
    override val IO: Scheduler = Schedulers.io()
}