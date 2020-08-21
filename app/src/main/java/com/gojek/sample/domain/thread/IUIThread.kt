package com.gojek.sample.domain.thread

import io.reactivex.Scheduler

interface IUIThread {
    fun getMainThread(): Scheduler
}
