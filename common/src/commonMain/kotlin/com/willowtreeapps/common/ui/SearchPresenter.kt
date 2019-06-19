package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.boundary.toBookListViewState
import org.reduxkotlin.SelectorSubscriberFn


class SearchPresenter(private val engine: LibraryApp,
                      private val networkThunks: NetworkThunks) : Presenter<SearchView>() {
    override fun recreateView() {
        //no-op
    }

    override fun makeSubscriber() = SelectorSubscriberFn<AppState>(engine.appStore) {
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

        withSingleField({it.searchBooks}) {
            view?.showResults(state.searchBooks.toBookListViewState())
        }
    }

    fun onTextChanged(query: String) {
        engine.dispatch(networkThunks.fetchBooks(query))
    }
}