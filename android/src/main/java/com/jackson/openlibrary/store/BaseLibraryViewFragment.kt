package com.jackson.openlibrary.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.external.*

abstract class BaseLibraryViewFragment<V: ViewWithProvider<AppState>>: Fragment(), ViewWithProvider<AppState> {

    private val presenterObserver = PresenterLifecycleObserver(this)
    private var viewRecreated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        lifecycle.addObserver(presenterObserver)
        super.onCreate(savedInstanceState)
    }
    /*

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
//        OpenLibraryApp.dispatch(AttachView(this))
        rootDispatch(AttachView(this))
        if (viewRecreated) {
            //TODO update view with all state
//            presenter?.recreateView()
        }
    }

     */

    /*
    override fun onPause() {
        super.onPause()
//        OpenLibraryApp.dispatch(DetachView(this))
        rootDispatch(DetachView(this))
    }

    override fun onDestroy() {
        super.onDestroy()
//        rootDispatch(ClearView(this))
//        OpenLibraryApp.dispatch(ClearView(this))
    }
     */
}


class PresenterLifecycleObserver(val view: ViewWithProvider<AppState>): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        rootDispatch(AttachView(view))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        rootDispatch(DetachView(view))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onClear() {
        rootDispatch(ClearView(view))
    }

}

/*
class PresenterMiddlewareViewModel: LifecycleObserver, ViewModel() {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        rootDispatch(AttachView(this))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        rootDispatch(AttachView(this))
    }
    override fun onCleared() {
        super.onCleared()
    }
}

 */