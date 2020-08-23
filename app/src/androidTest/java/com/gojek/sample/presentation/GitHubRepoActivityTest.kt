package com.gojek.sample.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.gojek.sample.R
import com.gojek.sample.presentation.adapter.GitHubItemAdapter
import com.gojek.sample.presentation.view.GitHubRepoActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class GitHubRepoActivityTest : GithubActivityRobot() {
    @get:Rule
    var activityTestRule = IntentsTestRule<GitHubRepoActivity>(GitHubRepoActivity::class.java)

    lateinit var activity :Activity
    @Before
    fun setup(){
        activity = activityTestRule.activity
    }


    @Test
    fun onLaunchCheckToolbarTitleIsDisplayed() {
        ActivityScenario.launch(GitHubRepoActivity::class.java)
        isDisplayed(R.id.toolBarTitleText)
    }

    @Test
    fun onLaunchCheckToolbarTitleIsDisplayedWithTextTrending() {
        ActivityScenario.launch(GitHubRepoActivity::class.java)
        checkTitleText(
            R.id.toolBarTitleText,
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.trending)
        )
    }

    @Test
    fun verifyGithubRecylerViewListItemTest() {
        ActivityScenario.launch(GitHubRepoActivity::class.java)
        if (getRVCount() > 0) {
            clickOnRecyclerViewItem(R.id.rvGitHubRepo, 0)
        } else {
            checkTitleText(
                R.id.noInternetMsg,
                getContext().getString(R.string.some_thing_went_wrong)
            )
            checkTitleText(
                R.id.retryButton,
                getContext().getString(R.string.str_retry)
            )
        }
    }

    fun getRVCount(): Int {
        val gitHubRecyclerView = activity.findViewById(R.id.rvGitHubRepo) as RecyclerView
        return gitHubRecyclerView.adapter?.itemCount ?: 0
    }

    private fun getContext():Context{
        return InstrumentationRegistry.getInstrumentation().targetContext
    }

}

open class GithubActivityRobot : ScreenRobot<GithubActivityRobot>() {

    fun checkTitleText(resId: Int, inputText: String): GithubActivityRobot {
        return checkViewHasText(resId, inputText)
    }

    fun clickOnRecyclerViewItem(resId: Int, position: Int): GithubActivityRobot {
        return clickOnRecycleItemView(resId, position)
    }

    fun isDisplayed(resId: Int): GithubActivityRobot {
        return checkIsDisplayed(resId)
    }
}