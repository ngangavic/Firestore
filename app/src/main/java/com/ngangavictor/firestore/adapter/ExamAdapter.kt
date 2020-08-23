package com.ngangavictor.firestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ExamHolder
import com.ngangavictor.firestore.listeners.ListenerDeleteExam
import com.ngangavictor.firestore.models.ExamModel

class ExamAdapter(
    private val context: Context,
    private val exams: ArrayList<ExamModel>,
    private val listenerDeleteExam: ListenerDeleteExam
) :
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
        holder.imageViewMore.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.imageViewMore)
            popupMenu.inflate(R.menu.exam_menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {
                        R.id.action_edit_exam -> {
                            return true
                        }
                        R.id.action_view_exam_results -> {
                            return true
                        }
                        R.id.action_enter_exam_results -> {
                            return true
                        }
                        R.id.action_delete_exam -> {
                            listenerDeleteExam.deleteExam(exams[position].key)
                            return true
                        }
                    }
                    return false
                }

            })
            popupMenu.show()

        }
    }

    override fun getItemCount(): Int {
        return exams.size
    }
}