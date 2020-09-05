package com.ngangavictor.firestore.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R

class StudentReportHolder (itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val textViewSubject: TextView = itemView.findViewById(R.id.textViewSubject)
    val textViewMarks: TextView = itemView.findViewById(R.id.textViewMarks)
    val textViewGrade: TextView = itemView.findViewById(R.id.textViewGrade)

}