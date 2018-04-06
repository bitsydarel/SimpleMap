package com.dbeginc.simplemap.domain

import io.reactivex.Scheduler

interface ThreadProvider {
    /**
     * User interface thread
     */
    val UI: Scheduler

    /**
     * Computation thread
     */
    val CP: Scheduler

    /**
     * Input/output thread
     */
    val IO: Scheduler
}