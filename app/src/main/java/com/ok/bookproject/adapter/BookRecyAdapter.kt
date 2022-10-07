package com.ok.bookproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.ok.bookproject.R
import com.ok.bookproject.model.BookDataModel
import javax.inject.Inject

class BookRecyAdapter @Inject constructor(
    val glide : RequestManager
): RecyclerView.Adapter<BookRecyAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<BookDataModel>(){
        override fun areItemsTheSame(oldItem: BookDataModel, newItem: BookDataModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BookDataModel, newItem: BookDataModel): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var books: List<BookDataModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_row,parent,false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageViewRow)
        val textView1 = holder.itemView.findViewById<TextView>(R.id.rowText1)
        val textView2 = holder.itemView.findViewById<TextView>(R.id.rowText2)
        val textView3 = holder.itemView.findViewById<TextView>(R.id.rowText3)
        val books = books[position]
        holder.itemView.apply {
            textView1.text = "Name: ${books.name}"
            textView2.text = "AuthorName: ${books.author}"
            textView3.text = "Year: ${books.year}"
            glide.load(books.imgUrl).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

}