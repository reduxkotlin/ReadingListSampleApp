package com.willowtreeapps.common.ui

import com.willowtreeapps.common.*
import com.willowtreeapps.common.repo.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.StoreSubscription
import kotlin.coroutines.CoroutineContext

/**
 * PresenterFactory that creates presenters for all views in the application.
 * Each view must attach/detach itself as it becomes visible/not visible.
 * Attaching returns a presenter to the view.
 * PresenterFactory subscribes to changes in state, and passes state to presenters.
 */
internal class PresenterFactory(private val libraryApp: LibraryApp,
                                bookRepository: BookRepository,
                                networkContext: CoroutineContext,
                                uiContext: CoroutineContext) : CoroutineScope {

    private val networkThunks = NetworkThunks(networkContext, bookRepository)
    private var subscription: StoreSubscription? = null

    private val toReadPresenter by lazy { ToReadPresenter(libraryApp, networkThunks) }
    private val completedPresenter by lazy { CompletedPresenter(libraryApp) }
    private val searchPresenter by lazy { SearchPresenter(libraryApp, networkThunks) }
    private val detailsPresenter by lazy { DetailsPresenter(libraryApp) }

    override val coroutineContext: CoroutineContext = uiContext + Job()

    fun <T : View<Presenter<*>>> attachView(view: T) {
        Logger.d("AttachView: $view", Logger.Category.LIFECYCLE)
        if (subscription == null) {
            subscription = libraryApp.appStore.subscribe(this::onStateChange)
        }
        //TODO find generic way to handle
        val presenter = when (view) {
            is ToReadView -> {
                toReadPresenter.attachView(view)
                toReadPresenter
            }
            is CompletedView -> {
                completedPresenter.attachView(view)
                completedPresenter
            }
            is SearchView -> {
                searchPresenter.attachView(view)
                searchPresenter
            }
            is DetailsView -> {
                detailsPresenter.attachView(view)
                detailsPresenter
            }
            else -> throw IllegalStateException("Screen $view not handled")
        }
        view.presenter = presenter
        presenter.onStateChange()
    }

    fun detachView(view: View<*>) {
        Logger.d("DetachView: $view", Logger.Category.LIFECYCLE)
        if (view is ToReadView)
            toReadPresenter.detachView(view)
        if (view is CompletedView)
            completedPresenter.detachView(view)
        if (view is SearchView)
            searchPresenter.detachView(view)
        if (view is DetailsView)
            detailsPresenter.detachView(view)

        if (hasAttachedViews()) {
            subscription?.invoke()
            subscription = null
        }
    }

    private fun hasAttachedViews() = toReadPresenter.isAttached()
            || completedPresenter.isAttached()
            || searchPresenter.isAttached()
            || detailsPresenter.isAttached()

    private fun onStateChange() {
        launch {
            if (toReadPresenter.isAttached()) {
                toReadPresenter.onStateChange()
            }
            if (completedPresenter.isAttached()) {
                completedPresenter.onStateChange()
            }
            if (searchPresenter.isAttached()) {
                searchPresenter.onStateChange()
            }
            if (detailsPresenter.isAttached()) {
                detailsPresenter.onStateChange()
            }
        }
//        presenters.forEach { it.onStateChange(libraryApp.appStore.state) }
    }
}

interface View<TPresenter> {
    var presenter: TPresenter
}

interface SingletonPresenterView<TPresenter>: View<TPresenter> {
}

abstract class Presenter<T : View<*>?> {
    var view: T? = null
    private var subscriber: StoreSubscriber? = null

    fun isAttached() = view != null

    open fun attachView(view: T) {
        Logger.d("Presenter attachView: $view", Logger.Category.LIFECYCLE)
        if (subscriber == null) {
            subscriber = makeSubscriber()
        }
        this.view = view
    }

    fun detachView(view: T) {
        Logger.d("Presenter DetachView: $view", Logger.Category.LIFECYCLE)
        if (this.view == view) {
            this.view = null
        }
    }

    /**
     * @return a StoreSubscriber for the presenter
     */
    abstract fun makeSubscriber(): StoreSubscriber

    fun onStateChange() {
        subscriber!!()
    }

    //on Android, this is when the view has been destroyed and must be recreated. (onConfig change, etc)
    //the state has not change, but the views must be set to the existing AppState
    abstract fun recreateView()

}

class PresenterProvider(val app: LibraryApp) {
    //uniqueId of view instance to its Presenter
    private val presenterMap = mapOf<String, Presenter<*>>()

    fun getPresenter(view: View<*>): Presenter<*> {

        if (view is SingletonPresenterView) {
            return presenterMap[view::class.qualifiedName]!!
        } else {
            val cachedPresenter = presenterMap[view.]
        }

    }
}
