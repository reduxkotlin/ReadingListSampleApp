package com.jackson.openlibrary.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jackson.openlibrary.GlideApp
import com.jackson.openlibrary.OpenLibraryApp
import com.jackson.openlibrary.R
import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.UiActions
import com.willowtreeapps.common.ui.BookListItemViewState
import com.willowtreeapps.common.ui.ListHeader
import kotlinx.android.synthetic.main.item_book.view.*
import kotlinx.android.synthetic.main.item_list_header.view.*

class BooksAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = listOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_book -> BookViewHolder(view)
            R.layout.item_list_header -> HeaderViewHolder(view)
            else -> throw NotImplementedError("BooksAdapter not handling type")
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ListHeader -> R.layout.item_list_header
            is BookListItemViewState -> R.layout.item_book
            else -> throw NotImplementedError("not handled in BookAdapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind((data[position] as ListHeader).title)
            is BookViewHolder -> holder.bind(data[position] as BookListItemViewState)
        }
    }

    fun setBooks(books: List<Any>) {
        data = books
        notifyDataSetChanged()
    }

}

class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(book: BookListItemViewState) {
        itemView.tvAuthor.text = book.author
        itemView.tvTitle.text = book.title
        GlideApp.with(itemView)
                .load(book.coverImageUrl)
                .into(itemView.ivBookCover)
        itemView.setOnClickListener { OpenLibraryApp.dispatch(UiActions.BookTapped(book)) }
    }

}
class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(title: String) {
        itemView.tvListTitle.text = title
    }
}

