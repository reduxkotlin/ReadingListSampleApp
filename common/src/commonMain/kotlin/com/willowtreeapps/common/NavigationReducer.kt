package com.willowtreeapps.common


val navigationReducer = reducer { state: AppState, action ->
    if (action is NavigationActions.GotoScreen) {
        state.copy(currentScreen = action.screen)
    } else {
        state
    }
}
