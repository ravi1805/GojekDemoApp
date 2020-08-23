package com.gojek.sample.presentation

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {
    @get:Rule
    var activityTestRule: IntentsTestRule<SplashActivity> =
        IntentsTestRule<SplashActivity>(SplashActivity::class.java,true,false)
    private val splashScreenWaitingTime = 1000L

    @Test
    fun testNavigateToGitHubScreenAfter3000ms() {
        val idlingResource: IdlingResource = ElapsedTimeIdlingResource(splashScreenWaitingTime)
        IdlingRegistry.getInstance().register(idlingResource)
        activityTestRule.launchActivity(null)
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

}