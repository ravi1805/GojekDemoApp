package com.gojek.sample.di.modules

import android.app.Application
import com.gojek.sample.service.INetworkClientService
import com.gojek.sample.service.NetworkClientFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    /*
   * The method returns the network client object
   * */
    @Singleton
    @Provides
    fun provideRetrofitNetworkService(application: Application): INetworkClientService {
        return NetworkClientFactory(application)
    }


}