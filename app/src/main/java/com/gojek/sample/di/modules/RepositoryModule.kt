package com.gojek.sample.di.modules

import com.gojek.sample.data.datasource.IRemoteDataTransaction
import com.gojek.sample.data.remote.RemoteTransactionManager
import com.gojek.sample.data.repository.GitHubDataRepoImpl
import com.gojek.sample.domain.repository.IGitHubDataRepo
import com.gojek.sample.service.INetworkClientService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteDataRepo(
        iNetworkClientService: INetworkClientService
    ): IRemoteDataTransaction {
        return RemoteTransactionManager(iNetworkClientService)
    }

    @Provides
    @Singleton
    fun provideDataRepo(
        iRemoteDataTransaction: IRemoteDataTransaction
    ): IGitHubDataRepo {
        return GitHubDataRepoImpl(iRemoteDataTransaction)
    }

}