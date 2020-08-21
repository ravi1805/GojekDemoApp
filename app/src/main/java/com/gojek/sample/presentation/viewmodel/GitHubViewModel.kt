package com.gojek.sample.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gojek.sample.domain.interactor.DefaultObserver
import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import com.gojek.sample.domain.usecase.GetGitHubRepoUseCase
import com.gojek.sample.presentation.utils.*
import com.gojek.sample.service.INetworkClientService
import javax.inject.Inject

class GitHubViewModel @Inject constructor(
    private val gitHubRepoUseCase: GetGitHubRepoUseCase,
    private val iNetworkClientService: INetworkClientService
) : ViewModel() {

    val TAG = "GitHubViewModel"

    val githubRepoLiveData = MutableLiveData<Resource<List<UIGitHubRepoData>>>()
    val errorMsgLiveData = MutableLiveData<String>()

    fun getGitHubRepo(request: GitHubRepoReq) {
        githubRepoLiveData.setLoading()
        if(iNetworkClientService.isMobileNetworkConnected()) {
            gitHubRepoUseCase.execute(GitHubRepoObserver(), request)
        }else{
            errorMsgLiveData.postValue(AppUtils.noNetworkMsg)
        }
    }

    private inner class GitHubRepoObserver : DefaultObserver<List<UIGitHubRepoData>>() {
        override fun onNext(dataList: List<UIGitHubRepoData>) {
            super.onNext(dataList)
            githubRepoLiveData.setSuccess(dataList)
        }

        override fun onError(exception: Throwable) {
            super.onError(exception)
            githubRepoLiveData.setError(exception.localizedMessage)
        }

    }

    override fun onCleared() {
        gitHubRepoUseCase.dispose()
        super.onCleared()
    }

}