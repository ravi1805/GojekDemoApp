package com.gojek.sample.data.repository

import com.gojek.sample.data.datasource.IRemoteDataTransaction
import com.gojek.sample.domain.model.UIRepoData
import com.gojek.sample.domain.repository.IGitHubDataRepo
import com.gojek.sample.domain.request.GitHubRepoReq
import io.reactivex.Observable
import javax.inject.Inject

class GitHubDataRepoImpl @Inject constructor(
    private val remoteDataTransaction: IRemoteDataTransaction
) :
    IGitHubDataRepo {

    override fun getGitHubRepository(request: GitHubRepoReq): Observable<List<UIRepoData>> {
       return remoteDataTransaction.getGitHubRepository(request)
    }

}