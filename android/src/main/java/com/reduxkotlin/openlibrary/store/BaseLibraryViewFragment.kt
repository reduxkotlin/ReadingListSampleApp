package com.reduxkotlin.openlibrary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.willowtreeapps.common.ui.LibraryBaseView
import org.reduxkotlin.*

abstract class BaseLibraryViewFragment<V: LibraryBaseView>: Fragment(), LibraryBaseView {
    private val presenterObserver = PresenterLifecycleObserver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        lifecycle.addObserver(presenterObserver)
        super.onCreate(savedInstanceState)
    }
}

