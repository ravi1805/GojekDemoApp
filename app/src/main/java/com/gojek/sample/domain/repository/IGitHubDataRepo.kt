package com.gojek.sample.domain.repository

import com.gojek.sample.domain.model.UIRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import io.reactivex.Observable

interface IGitHubDataRepo {
    fun getGitHubRepository(request: GitHubRepoReq): Observable<List<UIRepoData>>

}




