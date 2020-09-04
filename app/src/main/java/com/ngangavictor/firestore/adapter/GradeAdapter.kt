package com.ngangavictor.firestore.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.GradeHolder
import com.ngangavictor.firestore.listeners.ListenerGrade
import com.ngangavictor.firestore.models.GradeModel

class GradeAdapter(
    private val context: Context,
    private val grades: ArrayList<GradeModel>,
    private val listenerGrade: ListenerGrade
) :
    RecyclerView.Adapter<GradeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeHolder {
        val viewHolder: GradeHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_grade, parent, false)
        viewHolder = GradeHolder(layoutView)
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GradeHolder, position: Int) {
        holder.textViewGrade.text = grades[position].grade
        holder.textViewRange.text = grades[position].start + " ... " + grades[position].end

        holder.imageViewMore.setOnClickListener { }

        holder.editTextStartRange
        holder.editTextEndRange
    }

    override fun getItemCount(): Int {
        return grades.size
    }


}