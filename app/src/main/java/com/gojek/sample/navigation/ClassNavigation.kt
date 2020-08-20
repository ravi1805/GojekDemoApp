package com.gojek.sample.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.gojek.sample.R
import kotlin.jvm.internal.Intrinsics

object ClassNavigation {

    fun navigateScreen(
        callerActivity: Activity,
        activity: Class<*>,
        finishCallerActivity: Boolean,
        bundleData: Bundle? = null
    ) {
        Intrinsics.checkParameterIsNotNull(callerActivity, "callerActivity")
        Intrinsics.checkParameterIsNotNull(activity, "activity")
        val intent = Intent(callerActivity, activity)
        if (bundleData != null) {
            intent.putExtras(bundleData)
        }
        callerActivity.startActivity(intent)
        callerActivity.overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
        if (finishCallerActivity) {
            callerActivity.finish()
        }
    }


}


