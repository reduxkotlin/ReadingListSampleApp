package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.boundary.toBookListViewState
import com.willowtreeapps.common.SelectorSubscriberFn


class CompletedPresenter(private val engine: LibraryApp) : Presenter<CompletedView>() {
    override fun recreateView() {
        //no-op
    }

    override fun makeSubscriber() = SelectorSubscriberFn<AppState>(engine.appStore) {
        withSingleField({it.completed}) { view?.showBooks(state.completed.toList().toBookListViewState())}
        withSingleField({ it.isLoadingItems }) {
            if (state.isLoadingItems) {
                view?.showLoading()
            } else {
                view?.hideLoading()
            }
        }

        withSingleField({ it.errorLoadingItems }) {
            view?.showError(state.errorMsg)
        }
    }


    fun loadBooks() {
        engine.dispatch(Actions.LoadCompleted())
    }
}