package com.jackson.openlibrary.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackson.openlibrary.OpenLibraryApp
import com.jackson.openlibrary.R
import com.willowtreeapps.common.BookListItemViewState
import com.willowtreeapps.common.repo.Book
import com.willowtreeapps.common.ui.ToReadPresenter
import com.willowtreeapps.common.ui.ToReadView
import kotlinx.android.synthetic.main.fragment_to_read.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ToReadFragment : BaseLibraryViewFragment<ToReadPresenter>(), CoroutineScope, ToReadView {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override lateinit var presenter: ToReadPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_to_read, container, false)
    }

    private val adapter = BooksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        toReadRecycler.adapter = adapter
        toReadRecycler.layoutManager = LinearLayoutManager(context)
//        btn_start.setOnClickListener {
//            presenter.startGame()
//        }
//        btn_settings.setOnClickListener {
//            presenter.settingsTapped()
//        }
    }

    override fun onResume() {
        super.onResume()
        OpenLibraryApp.gameEngine().attachView(this)
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.gameEngine().detachView(this)
    }

    override fun hideLoading() {
        loading_spinner.visibility = View.GONE
    }

    override fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
    }

    override fun showError(msg: String) {
        txt_error.text = msg
    }

    override fun showBooks(toReadBook: List<BookListItemViewState>) {
        adapter.setBooks(toReadBook)
    }

}
