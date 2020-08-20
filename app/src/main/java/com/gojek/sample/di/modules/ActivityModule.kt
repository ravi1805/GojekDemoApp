package com.gojek.sample.di.modules

import com.gojek.sample.presentation.SplashActivity
import com.gojek.sample.presentation.view.GitHubRepoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): GitHubRepoActivity

}