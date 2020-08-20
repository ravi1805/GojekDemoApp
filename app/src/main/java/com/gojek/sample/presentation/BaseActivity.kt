package com.gojek.sample.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.gojek.sample.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onBackPressed() {
        this.finish()
        backNavigationAnimation()
    }

    protected fun backNavigationAnimation() {
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
    }

}