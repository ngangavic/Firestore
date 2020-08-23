package com.ngangavictor.firestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ExamHolder
import com.ngangavictor.firestore.holder.SubjectHolder
import com.ngangavictor.firestore.listeners.ListenerDeleteExam
import com.ngangavictor.firestore.models.ExamModel
import com.ngangavictor.firestore.models.SubjectModel

class SubjectAdapter(private val context: Context, private val subjects: ArrayList<SubjectModel>) :
    RecyclerView.Adapter<SubjectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder {
        val viewHolder: SubjectHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_subject, parent, false)
        viewHolder = SubjectHolder(layoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
        holder.textViewName.text=subjects[position].subjectName
        holder.imageViewMore.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return subjects.size
    }


}