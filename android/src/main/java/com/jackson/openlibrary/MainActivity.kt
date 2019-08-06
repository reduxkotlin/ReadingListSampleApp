package com.jackson.openlibrary

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.willowtreeapps.common.ui.UiActions
import com.willowtreeapps.hyperion.core.Hyperion
import kotlinx.android.synthetic.main.activity_main.*


@GlideModule
class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity() {

    interface IOnBackPressed {
        fun onBackPressed(): Boolean
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            toolbar.hideKeyboard()
        }
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(bottom_app_bar)
        fab.setOnClickListener {
            OpenLibraryApp.dispatch(UiActions.SearchBtnTapped())
            fab.hide()
        }
        toolbar.visibility = View.GONE

        navController.addOnDestinationChangedListener { controller, destination, args ->
            when(destination.id) {
                R.id.readingListFragment -> {
                    toolbar.visibility = View.GONE
                    toolbar.hideKeyboard()
                }
                R.id.completedListFragment -> {
                    toolbar.visibility = View.GONE
                    toolbar.hideKeyboard()
                }
                R.id.searchFragment -> {
                    toolbar.visibility = View.GONE
                    toolbar.showKeyboard()
                }
                R.id.detailsFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.hideKeyboard()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(
                        supportFragmentManager, bottomNavDrawerFragment.tag)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        /*
        val navHostFragment =
                this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        if (currentFragment is IOnBackPressed)
            (currentFragment as IOnBackPressed).onBackPressed()
         */
        super.onBackPressed()

    }

    fun showFab() {
        fab.show()
    }

    fun hideFab() {
        fab.hide()
    }

    var tripleTapDetector: View.OnTouchListener = object : View.OnTouchListener {
        internal var handler = Handler()

        internal var numberOfTaps = 0
        internal var lastTapTimeMs: Long = 0
        internal var touchDownMs: Long = 0

        override fun onTouch(v: View, event: MotionEvent): Boolean {

            when (event.action) {
                MotionEvent.ACTION_DOWN -> touchDownMs = System.currentTimeMillis()
                MotionEvent.ACTION_UP -> {
                    handler.removeCallbacksAndMessages(null)

                    if (System.currentTimeMillis() - touchDownMs > ViewConfiguration.getTapTimeout()) {
                        //it was not a tap

                        numberOfTaps = 0
                        lastTapTimeMs = 0
                        return true
                    }

                    if (numberOfTaps > 0 && System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getDoubleTapTimeout()) {
                        numberOfTaps += 1
                    } else {
                        numberOfTaps = 1
                    }

                    lastTapTimeMs = System.currentTimeMillis()

                    if (numberOfTaps == 3) {
                        Hyperion.open()
                        //handle triple tap
                    } else if (numberOfTaps == 2) {
                        handler.postDelayed({
                            //handle double tap
                        }, ViewConfiguration.getDoubleTapTimeout().toLong())
                    }
                }
            }

            return true
        }
    }
}
