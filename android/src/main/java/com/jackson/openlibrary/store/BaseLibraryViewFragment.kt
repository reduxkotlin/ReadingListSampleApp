package com.jackson.openlibrary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jackson.openlibrary.OpenLibraryApp
import com.willowtreeapps.common.*
import com.willowtreeapps.common.external.AttachView
import com.willowtreeapps.common.external.DetachView
import com.willowtreeapps.common.external.SelectorSubscriberBuilder
import com.willowtreeapps.common.ui.LibraryView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.reduxkotlin.Dispatcher
import kotlin.coroutines.CoroutineContext

open class BaseLibraryViewFragment<V: LibraryView>: Fragment(), LibraryView {

    private var viewRecreated: Boolean = false
    override lateinit var dispatch: Dispatcher
    override var selectorBuilder: SelectorSubscriberBuilder<AppState>? = null

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null)
            Logger.d("savedInstanceState == null")
        else {
            Logger.d("savedInstanceState != null")
            viewRecreated = true
        }
    }

    override fun onResume() {
        super.onResume()
        OpenLibraryApp.dispatch(AttachView(this))
        if (viewRecreated) {
            //TODO update view with all state
//            presenter?.recreateView()
        }
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.dispatch(DetachView(this))
    }
}


