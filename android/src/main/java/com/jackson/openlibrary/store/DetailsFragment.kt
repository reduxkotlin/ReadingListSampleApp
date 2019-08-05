package com.jackson.openlibrary.store

import android.os.Bundle
import android.view.*
import com.jackson.openlibrary.GlideApp
import com.jackson.openlibrary.MainActivity
import com.jackson.openlibrary.OpenLibraryApp.Companion.dispatch
import com.jackson.openlibrary.R
import com.jackson.openlibrary.tintAllIcons
import com.willowtreeapps.common.UiActions
import com.willowtreeapps.common.external.rootDispatch
import com.willowtreeapps.common.ui.BookDetailViewState
import com.willowtreeapps.common.ui.DetailsView
import kotlinx.android.synthetic.main.fragment_book_detail.*

class DetailsFragment : BaseLibraryViewFragment<DetailsView>(), DetailsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).hideFab()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.btm_app_bar_menu, menu)
        menu.tintAllIcons(R.color.white)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addToCompletedList -> rootDispatch(UiActions.AddToCompletedButtonTapped())
            R.id.addToReadingList -> rootDispatch(UiActions.AddToReadingButtonTapped())
        }
        return true
    }

    override fun render(vs: BookDetailViewState) {
        txtTitle.text = vs.book.title
        txtAuthorName.text = vs.book.author
        GlideApp.with(this)
                .load(vs.book.coverImageUrl)
                .into(imgBook)
    }
}
