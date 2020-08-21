package com.gojek.sample.domain.usecase

import com.gojek.sample.domain.interactor.BaseUseCase
import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.repository.IGitHubDataRepo
import com.gojek.sample.domain.request.GitHubRepoReq
import com.gojek.sample.domain.thread.IBackgroundThreadExecutor
import com.gojek.sample.domain.thread.IUIThread
import io.reactivex.Observable
import javax.inject.Inject

open class GetGitHubRepoUseCase
@Inject constructor(
    private val iGitHubDataRepo: IGitHubDataRepo,
    executor: IBackgroundThreadExecutor,
    thread: IUIThread
) : BaseUseCase<GitHubRepoReq, List<UIGitHubRepoData>>(executor, thread) {

    override fun build(request: GitHubRepoReq): Observable<List<UIGitHubRepoData>> {
        return iGitHubDataRepo.getGitHubRepository(request)

    }

}
