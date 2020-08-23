package com.gojek.sample.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gojek.sample.domain.repository.IGitHubDataRepo
import com.gojek.sample.domain.thread.IBackgroundThreadExecutor
import com.gojek.sample.domain.thread.IUIThread
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.mockito.Mock


open class BaseUseCaseTest {
    @get:Rule
    var instantiationExecutoreRule = InstantTaskExecutorRule()

    @Mock
    protected lateinit var mockPostExecutor: IUIThread

    @Mock
    protected lateinit var mockThreadExecutor: IBackgroundThreadExecutor

    @Mock
    protected lateinit var iGitHubDataRepo: IGitHubDataRepo

    @Mock
    protected lateinit var expectedException: ExpectedException
}
