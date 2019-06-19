package com.jackson.openlibrary.store

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackson.openlibrary.GlideApp
import com.jackson.openlibrary.OpenLibraryApp
import com.jackson.openlibrary.R
import com.willowtreeapps.common.BookListItemViewState
import com.willowtreeapps.common.ui.*
import kotlinx.android.synthetic.main.fragment_book_detail.*
import kotlinx.android.synthetic.main.fragment_book_detail.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_to_read.loading_spinner
import kotlinx.android.synthetic.main.fragment_to_read.txt_error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DetailsFragment : BaseLibraryViewFragment<DetailsPresenter>(), CoroutineScope, DetailsView {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override lateinit var presenter: DetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnToRead.setOnClickListener {
            presenter.toReadTapped()
        }
        btnCompleted.setOnClickListener {
            presenter.completedTapped()
        }
    }

    override fun onResume() {
        super.onResume()
        OpenLibraryApp.gameEngine().attachView(this)
    }

    override fun onPause() {
        super.onPause()
        OpenLibraryApp.gameEngine().detachView(this)
    }
    override fun render(book: BookListItemViewState) {
        txtTitle.text = book.title
        txtAuthorName.text = book.author
        GlideApp.with(this)
                .load(book.coverImageUrl)
                .into(imgBook)
    }
}
