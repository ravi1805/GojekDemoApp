package com.gojek.sample.domain.repository

import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import io.reactivex.Observable

interface IGitHubDataRepo {
    fun getGitHubRepository(request: GitHubRepoReq): Observable<List<UIGitHubRepoData>>

}




