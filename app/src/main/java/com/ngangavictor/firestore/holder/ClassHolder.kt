package com.ngangavictor.firestore.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import kotlinx.android.synthetic.main.row_class.view.*

class ClassHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val textViewName=itemView.findViewById<TextView>(R.id.textViewName)
    val imageViewMore=itemView.findViewById<ImageView>(R.id.imageViewMore)
}