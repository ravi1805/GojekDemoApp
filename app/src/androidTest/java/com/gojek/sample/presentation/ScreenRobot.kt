package com.gojek.sample.presentation

import android.app.Activity
import android.view.Gravity
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not


/* Screen Robot Class for generic Espresso Testing using Robot Pattern */

abstract class ScreenRobot<T : ScreenRobot<T>> {

    private var activityContext: Activity? = null // Only required for some calls

    fun checkIsDisplayed(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            onView(withId(viewId))
                .check(matches(ViewMatchers.isDisplayed()))
        }
        return this as T
    }

    fun checkIsHidden(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            onView(withId(viewId))
                .check(matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        }
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, expected: String): T {
        onView(withId(viewId))
            .check(matches(withText(expected)))
        return this as T
    }

    fun checkViewHasText(@IdRes viewId: Int, @StringRes messageResId: Int): T {
        onView(withId(viewId))
            .check(matches(withText(messageResId)))
        return this as T
    }

    fun checkViewHasHint(@IdRes viewId: Int, @StringRes messageResId: Int): T {
        onView(withId(viewId))
            .check(matches(withHint(messageResId)))
        return this as T
    }

    fun clickOkOnView(@IdRes viewId: Int): T {
        onView(withId(viewId))
            .perform(click())
        return this as T
    }

    fun clickOnViewWithText(text: String): T {
        onView(withText(text))
            .perform(click())
        return this as T
    }

    fun enterTextIntoView(@IdRes viewId: Int, text: String): T {
        onView(withId(viewId))
            .perform(ViewActions.typeText(text))
        return this as T
    }

    fun clickOnRecycleItemView(@IdRes viewId: Int, pos: Int): T {
        onView(withId(viewId))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    pos,
                    click()
                )
            )
        return this as T
    }

    fun clickOnNavigationButton(@IdRes viewId: Int, itemResId: Int): T {
        onView(withId(viewId))
            .perform(NavigationViewActions.navigateTo(itemResId))
        return this as T
    }

    fun openNavigation(@IdRes viewId: Int): T {
        onView(withId(viewId))
            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open());
        return this as T
    }

    fun isDisplayed(@IdRes vararg viewIds: Int): Boolean {
        for (viewId in viewIds) {
            onView(withId(viewId))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
        }
        return false
    }

    fun matchTextViewInsideParentLayout(resId: Int, parentId: Int, title: String): T {
        onView(
            allOf(
                withId(resId),
                isDescendantOfA(withId(parentId))
            )
        ).check(matches(ViewMatchers.isDisplayed()))
        if (title.isNotEmpty()) {
            onView(
                allOf(
                    withId(resId),
                    isDescendantOfA(withId(parentId))
                )
            ).check(matches(withText(title)))
        }
        return this as T
    }

    fun clickViewInsideParentLayout(resId: Int, parentId: Int): T {

        onView(allOf(withId(resId), withParent(withParent((withParent(withId(parentId))))))).check(
            matches(ViewMatchers.isDisplayed())
        ).perform(click())
        return this as T
    }


    fun provideActivityContext(activityContext: Activity): T {
        this.activityContext = activityContext
        return this as T
    }

    fun checkDialogWithTextIsDisplayed(@StringRes messageResId: Int): T {
        onView(withText(messageResId))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(activityContext!!.window.decorView)))
            .check(matches(ViewMatchers.isDisplayed()))
        return this as T
    }

    fun checkDesendent(@IdRes viewId: Int, text: String): T {
        onView(withId(viewId)).check(
            matches(hasDescendant(withText(text)))
        )
        return this as T
    }

    fun clickSpinnerItem(position: Int) {
        Espresso.onData(CoreMatchers.anything()).atPosition(position).perform(ViewActions.click())
    }

    fun matchSpinnerText(@IdRes spinnerId: Int, string: String) {
        onView(withId(spinnerId)).check(
            matches(
                withSpinnerText(
                    CoreMatchers.containsString(
                        string
                    )
                )
            )
        )
    }


    fun isDisabled(resId: Int): T {
        onView(withId(resId)).check(matches(not(isEnabled())))
        return this as T
    }

    fun isEnabled(resId: Int): T {
        onView(withId(resId)).check(matches(isEnabled()))
        return this as T
    }

    fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) =
            click().perform(uiController, view.findViewById<View>(viewId))
    }


    fun recycleviewchildViewClick(recycleViewId: Int, childViewId: Int, position: Int): T {
        onView(withId(recycleViewId)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                clickOnViewChild(childViewId)
            )
        )
        return this as T
    }

    fun scrollToWithId(resId: Int): T {
        onView(withId(resId))
            .perform(ViewActions.swipeUp())
        return this as T
    }

    fun scrollTo(text: String): T {
        onView(withText(text))
            .perform(ViewActions.swipeUp())
        return this as T
    }

    fun snackBarMatchTextandVisibility(resId: Int, text: String): T {
        onView(allOf(withId(resId), withText(text)))
            .check(matches(ViewMatchers.isDisplayed()));
        return this as T
    }


    companion object {

        fun <T : ScreenRobot<*>> withRobot(screenRobotClass: Class<T>?): T {
            if (screenRobotClass == null) {
                throw IllegalArgumentException("instance class == null") as Throwable
            }

            try {
                return screenRobotClass.newInstance()
            } catch (iae: IllegalAccessException) {
                throw RuntimeException("IllegalAccessException", iae)
            } catch (ie: InstantiationException) {
                throw RuntimeException("InstantiationException", ie)
            }

        }
    }


}