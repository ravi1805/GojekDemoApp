package com.gojek.sample.domain.usecase

import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetGitHubRepoUseCaseTest : BaseUseCaseTest() {

    private lateinit var getGitHubRepoUseCase: GetGitHubRepoUseCase

    @Mock
    private lateinit var gitHubListObserver: TestObserver<List<UIGitHubRepoData>>

    @Mock
    private lateinit var gitHubListObservable: Observable<List<UIGitHubRepoData>>
    
    private val gitHubRepoData = UIGitHubRepoData("1", "Test user", "","","","",10,3)

    private val gitHubRepoReq = GitHubRepoReq("Java","daily")
    @Before
    fun setup() {
        getGitHubRepoUseCase = GetGitHubRepoUseCase(iGitHubDataRepo, mockThreadExecutor, mockPostExecutor)
        expectedException = ExpectedException.none()
    }

    @Test
    fun `test data on success call`() {
        Mockito.`when`(getGitHubRepoUseCase.build(gitHubRepoReq)).thenReturn(
            Observable.just(
                listOf(
                    gitHubRepoData
                )
            )
        )
        gitHubListObservable = getGitHubRepoUseCase.build(gitHubRepoReq)
        gitHubListObserver = TestObserver()
        gitHubListObservable.subscribe(gitHubListObserver)
        gitHubListObserver.assertComplete().assertNoErrors().assertValue(listOf(gitHubRepoData))
    }

    @Test
    fun `test get githube list result on happy case`() {
        getGitHubRepoUseCase.build(gitHubRepoReq)
        verify(iGitHubDataRepo).getGitHubRepository(gitHubRepoReq)
        Mockito.verifyNoMoreInteractions(iGitHubDataRepo)
        Mockito.verifyZeroInteractions(mockThreadExecutor)
        Mockito.verifyZeroInteractions(mockPostExecutor)
    }

    @Test
    fun `test git hub list result on failur case`() {
        expectedException.expect(NullPointerException::class.java)
        getGitHubRepoUseCase.build(gitHubRepoReq)
    }

}