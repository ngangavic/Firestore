package com.ngangavictor.firestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ResultHolder
import com.ngangavictor.firestore.holder.StudentHolder
import com.ngangavictor.firestore.holder.SubjectHolder
import com.ngangavictor.firestore.models.ResultModel
import com.ngangavictor.firestore.models.StudentModel

class ResultAdapter (
    private val context: Context,
    private val results: ArrayList<ResultModel>
) :
    RecyclerView.Adapter<ResultHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val viewHolder: ResultHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_result, parent, false)
        viewHolder = ResultHolder(layoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.textViewAdm.text="Adm: "+results[position].adm
        holder.editTextMarks.setText(results[position].marks)
    }

    override fun getItemCount(): Int {
       return results.size
    }

}