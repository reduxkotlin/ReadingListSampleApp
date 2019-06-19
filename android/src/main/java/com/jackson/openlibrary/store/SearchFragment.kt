package com.jackson.openlibrary.store

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackson.openlibrary.OpenLibraryApp
import com.jackson.openlibrary.R
import com.willowtreeapps.common.BookListItemViewState
import com.willowtreeapps.common.ui.SearchPresenter
import com.willowtreeapps.common.ui.SearchView
import com.willowtreeapps.common.ui.ToReadPresenter
import com.willowtreeapps.common.ui.ToReadView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_to_read.loading_spinner
import kotlinx.android.synthetic.main.fragment_to_read.txt_error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.coroutines.CoroutineContext

class SearchFragment : BaseLibraryViewFragment<SearchPresenter>(), CoroutineScope, SearchView {
    private val adapter = BooksAdapter()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override lateinit var presenter: SearchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun showResults(books: List<BookListItemViewState>) {
        adapter.setBooks(books)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchRecycler.adapter = adapter
        searchRecycler.layoutManager = LinearLayoutManager(context)
        txt_search.addTextChangedListener(object : TextWatcher {
            var timer: Timer? = null
            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer?.schedule(object: TimerTask() {
                    override fun run() {
                        presenter?.onTextChanged(s.toString())
                    }

                }, 1000)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                timer?.cancel()
            }

        })
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
        presenter.startGame()
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
}
