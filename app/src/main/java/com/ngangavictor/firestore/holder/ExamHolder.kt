package com.ngangavictor.firestore.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import kotlinx.android.synthetic.main.row_exam.view.*

class ExamHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val imageViewMore:ImageView=itemView.findViewById(R.id.imageViewMore)

    val textViewName:TextView=itemView.findViewById(R.id.textViewName)
    val textViewTerm:TextView=itemView.findViewById(R.id.textViewTerm)
    val textViewYear:TextView=itemView.findViewById(R.id.textViewYear)
    val textViewClass:TextView=itemView.findViewById(R.id.textViewClass)

}