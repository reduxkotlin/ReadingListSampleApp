package org.reduxkotlin.readinglist.store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.reduxkotlin.readinglist.GlideApp
import org.reduxkotlin.readinglist.common.ui.BookListItemViewState
import org.reduxkotlin.readinglist.common.ui.ListHeader
import kotlinx.android.synthetic.main.item_book.view.*
import kotlinx.android.synthetic.main.item_list_header.view.*
import org.reduxkotlin.readinglist.R

class BooksAdapter(private val onClickListener: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            is BookViewHolder -> holder.bind(data[position] as BookListItemViewState, onClickListener)
        }
    }

    fun setBooks(books: List<Any>) {
        data = books
        notifyDataSetChanged()
    }

}

class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(book: BookListItemViewState, clickListener: (Int) -> Unit) {
        itemView.tvAuthor.text = book.author
        itemView.tvTitle.text = book.title
        GlideApp.with(itemView)
                .load(book.coverImageUrl)
                .into(itemView.ivBookCover)
        itemView.setOnClickListener {clickListener(adapterPosition)}
    }

}
class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(title: String) {
        itemView.tvListTitle.text = title
    }
}

