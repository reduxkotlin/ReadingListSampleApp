package org.reduxkotlin.readinglist.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import org.reduxkotlin.readinglist.common.ui.UiActions
import kotlinx.android.synthetic.main.activity_main.*
import org.reduxkotlin.readinglist.R
import org.reduxkotlin.readinglist.ReadingListApp
import org.reduxkotlin.readinglist.util.hideKeyboard
import org.reduxkotlin.readinglist.util.showKeyboard


@GlideModule
class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity() {

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
            ReadingListApp.dispatch(UiActions.SearchBtnTapped())
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

    fun showFab() {
        fab.show()
    }

    fun hideFab() {
        fab.hide()
    }
}
