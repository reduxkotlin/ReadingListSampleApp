package com.jackson.openlibrary.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackson.openlibrary.MainActivity
import com.jackson.openlibrary.OpenLibraryApp
import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.ui.ReadingListView
import kotlinx.android.synthetic.main.fragment_reading_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import com.jackson.openlibrary.R


class ReadingListFragment : BaseLibraryViewFragment<ReadingListView>(), CoroutineScope, ReadingListView {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reading_list, container, false)
    }

    private val adapter = BooksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toReadRecycler.adapter = adapter
        toReadRecycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        dispatch(Actions.LoadToRead())
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
