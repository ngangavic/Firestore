package com.ngangavictor.firestore.holder

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R

class ResultHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val textViewAdm: TextView = itemView.findViewById(R.id.textViewAdm)

    val editTextMarks: EditText = itemView.findViewById(R.id.editTextMarks)

}