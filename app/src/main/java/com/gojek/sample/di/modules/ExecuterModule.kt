package com.gojek.sample.di.modules

import com.gojek.sample.domain.thread.IBackgroundThreadExecutor
import com.gojek.sample.domain.thread.IBackgroundThreadExecutorImpl
import com.gojek.sample.domain.thread.IUIThread
import com.gojek.sample.domain.thread.UIThreadImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExecuterModule {


    @Provides
    @Singleton
    internal fun provideThreadExecutor(threadExecuterImpl: IBackgroundThreadExecutorImpl): IBackgroundThreadExecutor {
        return threadExecuterImpl
    }

    @Provides
    @Singleton
    internal fun providePostExecutionThread(iUiThreadImpl: UIThreadImpl): IUIThread {
        return iUiThreadImpl
    }


}
