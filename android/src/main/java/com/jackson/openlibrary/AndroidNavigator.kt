package com.jackson.openlibrary

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.jackson.openlibrary.store.CompletedFragment
import com.jackson.openlibrary.store.ReadingListFragment
import com.willowtreeapps.common.Logger
import com.willowtreeapps.common.middleware.Navigator
import com.willowtreeapps.common.middleware.Screen
import java.lang.Exception

/**
 * Android implementation of Navigator interface.  This will load the appropriate Activity or Fragment
 * using Android Navigation component.  Utilizes ActivityLifecycleCallbacks to keep reference of the current
 * Activity.
 */
class AndroidNavigator : Navigator, Application.ActivityLifecycleCallbacks {


    private var currentActivity: AppCompatActivity? = null
    private var cachedNavigationScreen: Screen? = null

    //TODO consider using current screen & destination screen to determine routing & animation
    override fun goto(screen: Screen) {
        if (currentActivity == null) {
            cachedNavigationScreen = screen
        } else {
//            val navController = currentActivity!!.findNavController(R.id.nav_host_fragment)

            when (screen) {
                Screen.BOOK_DETAILS -> currentActivity?.startActivity(Intent(currentActivity, DetailsActivity::class.java))
                Screen.COMPLETED_LIST -> navigateToFragment(currentActivity!!.supportFragmentManager.findFragmentByTag(CompletedFragment::class.java.name)
                        ?: CompletedFragment())
                Screen.READING_LIST -> navigateToFragment(currentActivity!!.supportFragmentManager.findFragmentByTag(ReadingListFragment::class.java.name)
                        ?: ReadingListFragment())
                else -> throw IllegalArgumentException("Screen $screen is not handled in AndroidNavigator")
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        val fragTransaction = currentActivity!!.supportFragmentManager.beginTransaction()
        fragTransaction.replace(R.id.nav_host_fragment, fragment, fragment::class.java.name)
        fragTransaction.commit()
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
        if (cachedNavigationScreen != null) {
            goto(cachedNavigationScreen!!)
            cachedNavigationScreen = null
        }
        attachActivity(activity)
    }

    private fun attachActivity(activity: Activity?) {
        try {
            currentActivity = activity as AppCompatActivity?
        } catch (e: Exception) {
            Logger.d("Exception casting activity to AppCompatActivity.  $e")
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        attachActivity(activity)
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        if (activity == currentActivity) {
            currentActivity = null
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}