package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.boundary.toBookListViewState
import com.willowtreeapps.common.SelectorSubscriberFn
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

typealias ViewUpdater<View> = (View) -> (Store) -> StoreSubscriber
typealias ViewUpdaterBuilder<State, View> = ((View.() -> ((SelectorSubscriberBuilder<State, View>.() -> Unit))))

class SearchPresenter(private val engine: LibraryApp,
                      private val networkThunks: NetworkThunks) : Presenter<SearchView>() {

    override fun recreateView() {
        //no-op
    }

    override fun makeSubscriber() = SelectorSubscriberFn<AppState, SearchView>(engine.appStore) {
        this+{it} + { }
        plus{}() {  }

        plus{ it.isLoadingItems} + {
            if (state.isLoadingItems) {
                view?.showLoading()
            } else {
                view?.hideLoading()
            }
        }


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
fun <View> presenter(actions: ViewUpdaterBuilder<AppState,View>): ViewUpdater<View> {
    return viewUpdater(actions)
}

val searchPresenter = presenter<SearchView> {
    {
        this+{it} + { }
        plus{}() {  }

        plus{ it.isLoadingItems} + {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        withSingleField({ it.isLoadingItems }) {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        withSingleField({ it.errorLoadingItems }) {
            showError(state.errorMsg)
        }

        withSingleField({it.searchBooks}) {
            showResults(state.searchBooks.toBookListViewState())
        }
    }
}