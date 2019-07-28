package com.willowtreeapps.common

import com.willowtreeapps.common.repo.Book
import com.willowtreeapps.common.ui.View

sealed class Actions {


    class FetchingItemsStartedAction
    data class FetchingItemsSuccessAction(val itemsHolder: List<Book>)
    data class FetchingItemsFailedAction(val message: String)

    data class BookSelected(val book: BookListItemViewState)

    class AddCurrentToCompleted
    class AddCurrentToRead


    class LoadAllSettingsAction
    data class ChangeNumQuestionsSettingsAction(val num: Int)
    data class ChangeMicrophoneModeSettingsAction(val enabled: Boolean)
    class LoadToRead
    data class ToReadLoaded(val books: List<Book>)
    class LoadCompleted
    data class CompletedLoaded(val books: List<Book>)
    data class AttachView<S: Any>(val view: View<S>)
}

