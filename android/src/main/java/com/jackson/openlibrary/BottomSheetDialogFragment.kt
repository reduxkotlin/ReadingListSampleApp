package com.jackson.openlibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jackson.openlibrary.store.CompletedFragment
import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.NavigationActions
import com.willowtreeapps.common.SelectorSubscriberBuilder
import com.willowtreeapps.common.middleware.Screen
import com.willowtreeapps.common.ui.BottomNavSheet
import com.willowtreeapps.common.ui.LibraryView
import kotlinx.android.synthetic.main.fragment_bottomsheet.*
import org.reduxkotlin.Dispatcher
import java.lang.IllegalArgumentException

class BottomNavigationDrawerFragment : BottomSheetDialogFragment(), BottomNavSheet {

    override lateinit var dispatch: Dispatcher
    override var selectorBuilder: SelectorSubscriberBuilder<AppState>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)
    }

    override fun onResume() {
        super.onResume()
        OpenLibraryApp.gameEngine().attachView(this)
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.gameEngine().detachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            // Bottom Navigation Drawer menu item clicks

            /*
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.readingList -> {
                    fragmentManager?.findFragmentByTag(ReadingListFragment::class.java.name)
                            ?: ReadingListFragment()
                }
                R.id.completedList -> {
                    fragmentManager?.findFragmentByTag(CompletedFragment::class.java.name)
                            ?: CompletedFragment()
                }
                else -> throw IllegalArgumentException()
            }

            val fragTransaction = fragmentManager?.beginTransaction()
            fragTransaction?.replace(R.id.nav_host_fragment, fragment, fragment::class.java.name)
            fragTransaction?.commit()
             */
            when (menuItem.itemId) {
                R.id.readingList -> dispatch(NavigationActions.GotoScreen(Screen.READING_LIST))
                R.id.completedList -> dispatch(NavigationActions.GotoScreen(Screen.COMPLETED_LIST))
            }
            dismiss()

            true
        }
    }
}