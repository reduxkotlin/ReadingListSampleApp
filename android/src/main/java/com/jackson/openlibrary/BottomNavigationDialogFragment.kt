package com.jackson.openlibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.willowtreeapps.common.external.AttachView
import com.willowtreeapps.common.external.DetachView
import com.willowtreeapps.common.external.rootDispatch
import com.willowtreeapps.common.ui.BottomNavSheet
import com.willowtreeapps.common.ui.UiActions
import kotlinx.android.synthetic.main.fragment_bottomsheet.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment(), BottomNavSheet {

//    override lateinit var dispatch: Dispatcher
//    override var selectorBuilder: SelectorSubscriberBuilder<AppState>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)
    }

    override fun onResume() {
        super.onResume()
        OpenLibraryApp.dispatch(AttachView(this))
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.dispatch(DetachView(this))
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
