package com.gojek.sample.domain.thread

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MainThread (UI Thread)   */
@Singleton
class UIThreadImpl
@Inject constructor()
    : IUIThread {

    override fun getMainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
