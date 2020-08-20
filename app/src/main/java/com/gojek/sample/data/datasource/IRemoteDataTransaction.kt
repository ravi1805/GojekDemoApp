package com.gojek.sample.data.datasource

import com.gojek.sample.domain.model.UIRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import io.reactivex.Observable

interface IRemoteDataTransaction {
    fun getGitHubRepository(request: GitHubRepoReq): Observable<List<UIRepoData>>
}