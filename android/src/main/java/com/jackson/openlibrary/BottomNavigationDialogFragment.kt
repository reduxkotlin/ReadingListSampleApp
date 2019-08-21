package com.jackson.openlibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.willowtreeapps.common.ui.BottomNavSheet
import com.willowtreeapps.common.ui.UiActions
import kotlinx.android.synthetic.main.fragment_bottomsheet.*
import org.reduxkotlin.PresenterLifecycleObserver
import org.reduxkotlin.rootDispatch

class BottomNavigationDrawerFragment : BottomSheetDialogFragment(), BottomNavSheet {

    private val presenterObserver = PresenterLifecycleObserver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenterObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.readingList -> rootDispatch(UiActions.ReadingListBtnTapped())
                R.id.completedList -> rootDispatch(UiActions.CompletedListBtnTapped())
            }
            dismiss()

            true
        }
    }
}
