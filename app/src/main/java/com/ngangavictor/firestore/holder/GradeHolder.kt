package com.ngangavictor.firestore.holder

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R

class GradeHolder (itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val imageViewMore: ImageView = itemView.findViewById(R.id.imageViewMore)

    val textViewGrade: TextView = itemView.findViewById(R.id.textViewGrade)
    val textViewRange: TextView = itemView.findViewById(R.id.textViewRange)

    val editTextEndRange: EditText = itemView.findViewById(R.id.editTextEndRange)
    val editTextStartRange: EditText = itemView.findViewById(R.id.editTextStartRange)
}