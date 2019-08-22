package org.reduxkotlin.readinglist.common.middleware

import org.reduxkotlin.readinglist.common.Actions
import org.reduxkotlin.readinglist.common.AppState
import org.reduxkotlin.readinglist.common.NavigationActions
import org.reduxkotlin.Middleware

internal fun navigationMiddleware(navigator: Navigator): Middleware<AppState> =
        { store ->
            { next ->
                { action ->
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
