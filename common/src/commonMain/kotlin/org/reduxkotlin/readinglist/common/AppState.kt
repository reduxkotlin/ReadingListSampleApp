package org.reduxkotlin.readinglist.common

import org.reduxkotlin.readinglist.common.middleware.Screen
import org.reduxkotlin.readinglist.common.repo.Book
import kotlin.reflect.KProperty0


data class AppState(val isLoadingItems: Boolean = false,
                    val searchBooks: List<Book> = listOf(),
                    val selectedBook: Book? = null,
                    val errorLoadingItems: Boolean = false,
                    val readingList: List<Book> = listOf(),
                    val completedList: List<Book> = listOf(),
                    //experimental
                    val currentList: KProperty0<List<Book>>? = null,
                    val errorMsg: String = "",
                    val currentScreen: Screen = Screen.READING_LIST,
                    val settings: UserSettings = UserSettings.defaults()) {
    companion object {
        val INITIAL_STATE = AppState()
    }


    fun currentSearchIndex() = currentList?.invoke()?.indexOf(selectedBook) ?: 0
}

data class UserSettings(val numQuestions: Int,
                        val isWillowTree: Boolean = false,
                        val microphoneMode: Boolean) {
    companion object {
        fun defaults() = UserSettings(3,
                microphoneMode = false)
    }
}
