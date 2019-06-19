package com.jackson.openlibrary

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.jackson.openlibrary.store.CompletedFragment
import com.jackson.openlibrary.store.SearchFragment
import com.jackson.openlibrary.store.ToReadFragment
import com.willowtreeapps.hyperion.core.Hyperion
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class DetailsActivity : AppCompatActivity() {

    interface IOnBackPressed {
        fun onBackPressed(): Boolean
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        val navHostFragment =
                this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        if (currentFragment is IOnBackPressed)
            (currentFragment as IOnBackPressed).onBackPressed()
        super.onBackPressed()
    }
}
