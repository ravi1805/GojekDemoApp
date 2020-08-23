package com.gojek.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Extension function to observe changes in LiveData and get the updated Value
 */

fun <T> LiveData<T>.getOrAwaitValue(time: Long = 2, timeUnit: TimeUnit = TimeUnit.SECONDS): T {
    var data: T? = null
    var latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T?) {
            data = t
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    if (!latch.await(time, timeUnit)) {
        throw  TimeoutException("Live data value was never set.")
    }

    return data as T

}
