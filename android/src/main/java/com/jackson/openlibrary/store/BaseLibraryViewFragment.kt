package com.jackson.openlibrary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.willowtreeapps.common.Logger
import com.jackson.openlibrary.OpenLibraryApp
import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.SelectorSubscriberBuilder
import com.willowtreeapps.common.ui.LibraryProvider
import com.willowtreeapps.common.ui.LibraryView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.reduxkotlin.Dispatcher
import kotlin.coroutines.CoroutineContext

open class BaseLibraryViewFragment<V: LibraryView>: Fragment(),
        CoroutineScope, LibraryView, LibraryProvider by OpenLibraryApp.gameEngine() {
    override lateinit var dispatch: Dispatcher
    override lateinit var selectorBuilder: SelectorSubscriberBuilder<AppState>

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var viewRecreated: Boolean = false

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
        OpenLibraryApp.gameEngine().attachView(this)
        if (viewRecreated) {
            //TODO update view with all state
//            presenter?.recreateView()
        }
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.gameEngine().detachView(this)
    }
}
