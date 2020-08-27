package com.ngangavictor.firestore.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R

class StudentHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val imageViewMore: ImageView = itemView.findViewById(R.id.imageViewMore)

    val textViewName: TextView = itemView.findViewById(R.id.textViewName)
    val textViewAdm: TextView = itemView.findViewById(R.id.textViewAdm)
    val textViewKCPE: TextView = itemView.findViewById(R.id.textViewKCPE)
    val textViewParentNo: TextView = itemView.findViewById(R.id.textViewParentNo)
}