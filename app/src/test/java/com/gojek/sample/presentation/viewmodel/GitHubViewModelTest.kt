package com.gojek.sample.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import com.gojek.sample.domain.usecase.GetGitHubRepoUseCase
import com.gojek.sample.getOrAwaitValue
import com.gojek.sample.presentation.utils.Resource
import com.gojek.sample.presentation.utils.ResourceState
import com.gojek.sample.presentation.utils.setSuccess
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHubViewModelTest {
    @get:Rule
    var instantiationExecutoreRule = InstantTaskExecutorRule()

    private lateinit var gitHubViewModel: GitHubViewModel

    @Mock
    private lateinit var getGitHubRepoUseCase: GetGitHubRepoUseCase

    private var gitHubRepoReq = GitHubRepoReq("Java", "daily")

    @Mock
    private lateinit var resultObserver: Observer<Resource<List<UIGitHubRepoData>>>

    @Mock
    private lateinit var gitHubListObserver: TestObserver<List<UIGitHubRepoData>>

    @Mock
    private lateinit var gitHubListObservable: Observable<List<UIGitHubRepoData>>

    @Mock
    private lateinit var gitHubList: List<UIGitHubRepoData>

    @Before
    fun setup() {
        gitHubViewModel = GitHubViewModel(getGitHubRepoUseCase)
        gitHubViewModel.githubRepoLiveData.observeForever(resultObserver)
    }


    @Test
    fun `handle githubLive data observer`() {
        assertNotNull(gitHubViewModel.githubRepoLiveData)
        assertTrue(gitHubViewModel.githubRepoLiveData.hasObservers())
    }

    @Test
    fun `fetch github loading state`() {
        gitHubViewModel.getGitHubRepo(gitHubRepoReq)
        Mockito.verify(resultObserver).onChanged(Resource(ResourceState.LOADING))
    }

    @Test
    fun `fetch gitHub item loading state with null data`() {
        gitHubViewModel.getGitHubRepo(gitHubRepoReq)
        assert(gitHubViewModel.githubRepoLiveData.getOrAwaitValue().state == ResourceState.LOADING)
        assert(gitHubViewModel.githubRepoLiveData.getOrAwaitValue().data?.none() == null)
    }


    @Test
    fun `fetch github Result State Not Empty`() {

        Mockito.`when`(getGitHubRepoUseCase.build(gitHubRepoReq))
            .thenReturn(Observable.just(gitHubList))
        gitHubListObservable = getGitHubRepoUseCase.build(gitHubRepoReq)
        gitHubListObserver = TestObserver()

        gitHubListObservable.subscribe(gitHubListObserver)

        gitHubListObserver.assertComplete()
            .assertNoErrors()
            .assertValue(gitHubList)

        assertNotNull(gitHubViewModel.githubRepoLiveData.setSuccess(gitHubList))
        gitHubListObserver.onNext(gitHubList)

    }
}
