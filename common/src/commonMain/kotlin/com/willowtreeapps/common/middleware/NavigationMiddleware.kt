package com.willowtreeapps.common.middleware

import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.NavigationActions
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Store

internal class NavigationMiddleware(private val navigator: Navigator) {

    fun dispatch(store: Store) = { next: Dispatcher ->
        { action: Any ->
            when (action) {
                is NavigationActions.GotoScreen -> navigator.goto(action.screen)
            }
            next(action)
            when (action) {
                is Actions.PrevBook, is Actions.NextBook -> navigator.goto(Screen.BOOK_DETAILS)
            }
        }
    }
}

enum class Screen {
    READING_LIST,
    COMPLETED_LIST,
    SEARCH,
    BOOK_DETAILS,
}

interface Navigator {
    fun goto(screen: Screen)
}
