package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.boundary.toBookListViewState
import com.willowtreeapps.common.SelectorSubscriberFn


class ToReadPresenter(private val engine: LibraryApp,
                      private val networkThunks: NetworkThunks) : Presenter<ToReadView>() {
    override fun recreateView() {
        //no-op
    }

    override fun makeSubscriber() = SelectorSubscriberFn<AppState>(engine.appStore) {
        withSingleField({it.toReadBook}) { view?.showBooks(state.toReadBook.toList().toBookListViewState())}

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
        engine.dispatch(Actions.LoadToRead())
    }

}