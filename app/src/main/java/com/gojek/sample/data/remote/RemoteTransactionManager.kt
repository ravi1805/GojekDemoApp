package com.gojek.sample.data.remote

import com.gojek.sample.data.remote.api.IRemoteServiceApi
import com.gojek.sample.data.datasource.IRemoteDataTransaction
import com.gojek.sample.data.remote.response.GitHubApiResponse
import com.gojek.sample.data.remote.response.mapToDomain
import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import com.gojek.sample.service.INetworkClientService
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import javax.inject.Inject

class RemoteTransactionManager @Inject constructor(
    private val networkService: INetworkClientService
) :
    IRemoteDataTransaction {

    override fun getGitHubRepository(request: GitHubRepoReq): Observable<List<UIGitHubRepoData>> {
        return Observable.create<List<GitHubApiResponse>> { emitter ->
            val call = getApiService().getGitHubRepo(request.language, request.sinceTime)
            val callback =
                networkService.getJsonCallback(emitter, object : TypeToken<List<GitHubApiResponse>>(){})
            call.enqueue(callback)
        }.flatMap { result ->
            Observable.just(result.mapToDomain())
        }
    }

    private fun getApiService(): IRemoteServiceApi {
        return networkService.getNetworkClient(IRemoteServiceApi::class.java)
    }

}

