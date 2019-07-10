package com.jackson.openlibrary.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackson.openlibrary.OpenLibraryApp
import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.BookListItemViewState
import com.willowtreeapps.common.ui.ReadingListView
import kotlinx.android.synthetic.main.fragment_reading_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import android.graphics.drawable.InsetDrawable
import com.jackson.openlibrary.R


class ReadingListFragment : BaseLibraryViewFragment<ReadingListView>(), CoroutineScope, ReadingListView {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reading_list, container, false)
    }

    private val adapter = BooksAdapter()

    val divider by lazy {
        val attrs = intArrayOf(android.R.attr.listDivider)
        val a = context?.obtainStyledAttributes(attrs)
        val divider = a?.getDrawable(0)
        val inset = resources.getDimensionPixelSize(com.jackson.openlibrary.R.dimen.list_divider_margin_start)
        val insetDivider = InsetDrawable(divider, inset, 0, 0, 0)
        a?.recycle()
        insetDivider
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toReadRecycler.adapter = adapter
        toReadRecycler.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        dividerItemDecoration.setDrawable(divider)
        toReadRecycler.addItemDecoration(dividerItemDecoration)
    }

    override fun onResume() {
        super.onResume()
        dispatch(Actions.LoadToRead())
//        presenter?.loadBooks()
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

    override fun showTitle(title: String) {
        readingListTitle.text = title
    }

}
