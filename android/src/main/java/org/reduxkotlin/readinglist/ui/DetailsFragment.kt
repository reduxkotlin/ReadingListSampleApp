package org.reduxkotlin.readinglist.ui

import android.os.Bundle
import android.view.*
import org.reduxkotlin.readinglist.*
import org.reduxkotlin.readinglist.common.ui.UiActions
import org.reduxkotlin.readinglist.common.ui.BookDetailViewState
import org.reduxkotlin.readinglist.common.ui.DetailsView
import kotlinx.android.synthetic.main.fragment_book_detail.*
import org.reduxkotlin.readinglist.util.MenuTint
import org.reduxkotlin.rootDispatch

class DetailsFragment : BaseLibraryViewFragment<DetailsView>(), DetailsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).hideFab()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.btm_app_bar_menu, menu)
        val color = getResources().getColor(R.color.white)
        MenuTint.on(menu).setMenuItemIconColor(color).apply(activity)
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
        txtPublishDates.text = vs.publishedDate
        txtPublisher.text = vs.publisher
        txtSubject.text = vs.subjects.firstOrNull() ?: ""
    }
}
