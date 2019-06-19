package com.jackson.openlibrary.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jackson.openlibrary.GlideApp
import com.jackson.openlibrary.OpenLibraryApp
import com.jackson.openlibrary.R
import com.willowtreeapps.common.Actions
import com.willowtreeapps.common.BookListItemViewState
import kotlinx.android.synthetic.main.item_book.view.*

class BooksAdapter: RecyclerView.Adapter<BookViewHolder>() {
    private var data = listOf<BookListItemViewState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setBooks(books: List<BookListItemViewState>) {
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
        itemView.setOnClickListener { OpenLibraryApp.gameEngine().dispatch(Actions.BookSelected(book)) }
    }

}