package com.willowtreeapps.common

import com.willowtreeapps.common.repo.Book


data class AppState(val isLoadingItems: Boolean = false,
                    val items: List<Book> = listOf(),
                    val selectedBook: Book? = null,
                    val errorLoadingItems: Boolean = false,
                    val toReadBook: Set<Book> = setOf(),
                    val completed: Set<Book> = setOf(),
                    val errorMsg: String = "",
                    val settings: UserSettings = UserSettings.defaults()) {
    companion object {
        val INITIAL_STATE = AppState()
    }

    fun bookForId(id: String) = items.first { it.openLibraryId == id }

}

data class UserSettings(val numQuestions: Int,
                        val isWillowTree: Boolean = false,
                        val microphoneMode: Boolean) {
    companion object {
        fun defaults() = UserSettings(3,
                microphoneMode = false)
    }
}
