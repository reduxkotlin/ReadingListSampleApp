package org.reduxkotlin.readinglist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.reduxkotlin.readinglist.common.ui.ReadingListView
import kotlinx.android.synthetic.main.fragment_reading_list.*
import org.reduxkotlin.readinglist.R
import org.reduxkotlin.readinglist.common.ui.UiActions
import org.reduxkotlin.rootDispatch


class ReadingListFragment : BaseLibraryViewFragment<ReadingListView>(), ReadingListView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reading_list, container, false)
    }

    private val adapter = BooksAdapter { pos -> rootDispatch(UiActions.ReadingListBookTapped(pos - 1))}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toReadRecycler.adapter = adapter
        toReadRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        rootDispatch(UiActions.ReadingListShown())
        (activity as MainActivity).showFab()
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

    override fun showBooks(toReadBook: List<Any>) {
        adapter.setBooks(toReadBook)
    }
}
