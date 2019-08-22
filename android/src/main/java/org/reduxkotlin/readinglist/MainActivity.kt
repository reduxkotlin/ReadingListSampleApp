package org.reduxkotlin.readinglist

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
}
