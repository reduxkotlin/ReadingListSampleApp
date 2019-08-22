package com.reduxkotlin.openlibrary.store

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.reduxkotlin.openlibrary.R
import com.reduxkotlin.openlibrary.showKeyboard
import com.willowtreeapps.common.ui.BookListItemViewState
import com.willowtreeapps.common.ui.SearchView
import com.willowtreeapps.common.ui.UiActions
import kotlinx.android.synthetic.main.fragment_reading_list.loading_spinner
import kotlinx.android.synthetic.main.fragment_reading_list.txt_error
import kotlinx.android.synthetic.main.fragment_search.*
import org.reduxkotlin.rootDispatch
import java.util.*


class SearchFragment : BaseLibraryViewFragment<SearchView>(), SearchView {
    private val adapter = BooksAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun showResults(books: List<BookListItemViewState>) {
        adapter.setBooks(books)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        txt_search.requestFocus()
        txt_search.showKeyboard()

        searchRecycler.adapter = adapter
        searchRecycler.layoutManager = LinearLayoutManager(context)
        val ignoreNextTextChange = savedInstanceState != null
        txt_search.addTextChangedListener(object : TextWatcher {
            var timer: Timer? = null
            override fun afterTextChanged(s: Editable?) {
                if (!ignoreNextTextChange) {
                    timer = Timer()
                    timer?.schedule(object : TimerTask() {
                        override fun run() {
                            rootDispatch(UiActions.SearchQueryEntered(s.toString()))
                        }

                    }, 1000)
                }

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
