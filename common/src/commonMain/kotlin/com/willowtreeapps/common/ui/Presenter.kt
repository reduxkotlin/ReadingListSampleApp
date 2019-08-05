package com.willowtreeapps.common.ui

import com.willowtreeapps.common.AppState
import com.willowtreeapps.common.external.*

//a Presenter typed to our app's State type for convenience
fun <V: LibraryView> presenter(actions: PresenterBuilder<AppState, V>): Presenter<View> {
    return createGenericPresenter(actions) as Presenter<View>
}

fun <V: LibraryView> presenterWithViewArg(actions: PresenterBuilderWithViewArg<AppState, V>): Presenter<View> {
    return createGenericPresenter(actions) as Presenter<View>
}
