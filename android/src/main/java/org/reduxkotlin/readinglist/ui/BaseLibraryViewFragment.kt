package org.reduxkotlin.readinglist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.reduxkotlin.readinglist.common.ui.LibraryBaseView
import org.reduxkotlin.*

abstract class BaseLibraryViewFragment<V: LibraryBaseView>: Fragment(), LibraryBaseView {
    private val presenterObserver = PresenterLifecycleObserver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        lifecycle.addObserver(presenterObserver)
        super.onCreate(savedInstanceState)
    }
}

