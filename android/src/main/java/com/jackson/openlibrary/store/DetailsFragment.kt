package com.jackson.openlibrary.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jackson.openlibrary.GlideApp
import com.jackson.openlibrary.R
import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.UiActions
import com.willowtreeapps.common.ui.BookDetailViewState
import com.willowtreeapps.common.ui.*
import kotlinx.android.synthetic.main.fragment_book_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DetailsFragment : BaseLibraryViewFragment<DetailsView>(), CoroutineScope, DetailsView {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun render(vs: BookDetailViewState) {
        txtTitle.text = vs.book.title
        txtAuthorName.text = vs.book.author
        GlideApp.with(this)
                .load(vs.book.coverImageUrl)
                .into(imgBook)
        btnToRead.setOnClickListener {
            dispatch(UiActions.AddToReadingButtonTapped())
        }
        btnCompleted.setOnClickListener {
            dispatch(UiActions.AddToCompletedButtonTapped())
        }
    }
}
