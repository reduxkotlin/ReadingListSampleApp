package org.reduxkotlin.readinglist.common.ui

import org.reduxkotlin.readinglist.common.boundary.toBookListViewState


interface ReadingListView : LibraryBaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String)
    fun showBooks(toReadBook: List<Any>)
    override fun presenter() = readingListPresenter
}

const val READING_LIST_TITLE = "Reading List"

val readingListPresenter = presenter<ReadingListView> {{
        +{ state.readingList } + { showBooks(state.readingList.toBookListViewState(READING_LIST_TITLE)) }

        +{ state.isLoadingItems } + {
            if (state.isLoadingItems) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        +{ state.errorLoadingItems } + { showError(state.errorMsg) }
    }}