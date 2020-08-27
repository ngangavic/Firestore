package com.ngangavictor.firestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ExamHolder
import com.ngangavictor.firestore.holder.StudentHolder
import com.ngangavictor.firestore.listeners.ListenerSubject
import com.ngangavictor.firestore.models.StudentModel
import com.ngangavictor.firestore.models.SubjectModel

class StudentAdapter (
    private val context: Context,
private val students: ArrayList<StudentModel>
) :
RecyclerView.Adapter<StudentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val viewHolder: StudentHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_student, parent, false)
        viewHolder = StudentHolder(layoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        holder.textViewAdm.text=students[position].adm
        holder.textViewName.text=students[position].name
        holder.textViewParentNo.text=students[position].parentNo
        holder.textViewKCPE.text=students[position].kcpe
        holder.imageViewMore.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return students.size
    }


}