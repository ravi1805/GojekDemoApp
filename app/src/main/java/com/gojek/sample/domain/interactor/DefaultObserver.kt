package com.gojek.sample.domain.interactor

import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

open class DefaultObserver<T> : DisposableObserver<T>() {

    private var latch: CountDownLatch? = null

    override fun onStart() {
        super.onStart()
        latch = CountDownLatch(1)
    }

    override fun onNext(@NonNull t: T) {
        // no-op by default.
        if (latch != null) {
            latch!!.countDown()
        }
    }

    override fun onComplete() {
        // no-op by default.
        if (latch != null) {
            latch!!.countDown()
        }
    }

    override fun onError(@NonNull exception: Throwable) {
        exception.printStackTrace()
        if (latch != null) {
            latch!!.countDown()
        }
    }

    fun await(timeout: Long, unit: TimeUnit) {
        try {
            latch = CountDownLatch(1)
            latch!!.await(timeout, unit)
        } catch (ex: Exception) {
        }

    }
}
