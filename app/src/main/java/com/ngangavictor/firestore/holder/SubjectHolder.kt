package com.ngangavictor.firestore.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R

class SubjectHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val imageViewMore: ImageView = itemView.findViewById(R.id.imageViewMore)

    val textViewName: TextView = itemView.findViewById(R.id.textViewName)
}