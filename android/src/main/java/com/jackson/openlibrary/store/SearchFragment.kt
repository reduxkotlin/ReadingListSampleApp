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
import com.willowtreeapps.common.ui.SearchView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_to_read.loading_spinner
import kotlinx.android.synthetic.main.fragment_to_read.txt_error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.coroutines.CoroutineContext

class SearchFragment : BaseLibraryViewFragment<SearchView>(), CoroutineScope, SearchView {
    private val adapter = BooksAdapter()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


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
                        //could be a regular action with network middleware?
                        dispatch(OpenLibraryApp.gameEngine().networkThunks.fetchBooks(s.toString()))
//                        presenter?.onTextChanged(s.toString())
                    }

                }, 1000)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                timer?.cancel()
            }

        })
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
