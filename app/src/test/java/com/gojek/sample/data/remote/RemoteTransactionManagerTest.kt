package com.gojek.sample.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gojek.sample.data.datasource.IRemoteDataTransaction
import com.gojek.sample.data.repository.GitHubDataRepoImpl
import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteTransactionManagerTest{

    @get:Rule
    var instantiationExecutoreRule = InstantTaskExecutorRule()

    private lateinit var gitHubDataRepoImpl: GitHubDataRepoImpl


    @Mock
    private lateinit var iRemoteDataTransaction: IRemoteDataTransaction

    private var gitHubRepoReq = GitHubRepoReq("Java", "daily")
    private val gitHubRepoData = UIGitHubRepoData("1", "Test user", "","","","",10,3)

    @Before
    fun setUp() {
        gitHubDataRepoImpl = GitHubDataRepoImpl(iRemoteDataTransaction)
    }


    @Test
    fun `get github trending list item when connected to internet`(){
        Mockito.`when`(iRemoteDataTransaction.getGitHubRepository(gitHubRepoReq)).thenReturn(
            Observable.just(
                listOf(gitHubRepoData)))
        val result = gitHubDataRepoImpl.getGitHubRepository(gitHubRepoReq)
        assertEquals(1, result.blockingFirst().size)
    }

    @Test
    fun `get empty github trending items when not connected to internet`(){
        Mockito.`when`(iRemoteDataTransaction.getGitHubRepository(gitHubRepoReq)).thenReturn(
            Observable.just(
                listOf()))
        val result = gitHubDataRepoImpl.getGitHubRepository(gitHubRepoReq)
        assertEquals(0, result.blockingFirst().size)
    }

}