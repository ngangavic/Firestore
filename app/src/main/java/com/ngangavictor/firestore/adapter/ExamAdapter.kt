package com.ngangavictor.firestore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ExamHolder
import com.ngangavictor.firestore.models.ExamModel

class ExamAdapter(private val exams: ArrayList<ExamModel>) :
    RecyclerView.Adapter<ExamHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamHolder {
        val viewHolder: ExamHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_exam, parent, false)
        viewHolder = ExamHolder(layoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ExamHolder, position: Int) {
        holder.textViewName.text = exams[position].examName
        holder.textViewClass.text = exams[position].examClass
        holder.textViewTerm.text = exams[position].examTerm
        holder.textViewYear.text = exams[position].examYear
        holder.imageViewMore.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return exams.size
    }
}