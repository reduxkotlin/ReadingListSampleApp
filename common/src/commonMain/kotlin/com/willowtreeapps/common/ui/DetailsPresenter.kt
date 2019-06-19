package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.boundary.toBookListViewState
import org.reduxkotlin.StoreSubscriber


class DetailsPresenter(private val engine: GameEngine,
                       private val networkThunks: NetworkThunks) : Presenter<DetailsView>() {
    override fun recreateView() {
        //no-op
    }

    override fun makeSubscriber(): StoreSubscriber = {
        view?.render(engine.state.selectedBook!!.toBookListViewState())
        Unit
    }

    fun toReadTapped() {
        engine.dispatch(Actions.AddToRead(engine.state.selectedBook!!))
    }
    fun completedTapped() {
        engine.dispatch(Actions.AddToCompleted(engine.state.selectedBook!!))
    }

}