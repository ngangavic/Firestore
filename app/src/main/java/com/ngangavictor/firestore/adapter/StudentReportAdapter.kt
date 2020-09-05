package com.ngangavictor.firestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.StudentReportHolder
import com.ngangavictor.firestore.holder.SubjectHolder
import com.ngangavictor.firestore.listeners.ListenerSubject
import com.ngangavictor.firestore.models.StudentReportModel
import com.ngangavictor.firestore.models.SubjectModel

class StudentReportAdapter(
    private val context: Context,
    private val studentReport: ArrayList<StudentReportModel>
) :
    RecyclerView.Adapter<StudentReportHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentReportHolder {
        val viewHolder: StudentReportHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_student_report, parent, false)
        viewHolder = StudentReportHolder(layoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: StudentReportHolder, position: Int) {
        holder.textViewSubject.text=studentReport[position].subject
        holder.textViewMarks.text=studentReport[position].marks.toString()
        holder.textViewGrade.text=studentReport[position].grade
    }

    override fun getItemCount(): Int {
        return studentReport.size
    }


}