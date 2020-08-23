package com.ngangavictor.firestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.SubjectHolder
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
        holder.textViewName.text = subjects[position].subjectName
        holder.imageViewMore.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.imageViewMore)
            popupMenu.inflate(R.menu.subject_menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {
                        R.id.action_edit_subject -> {
                            return true
                        }
                        R.id.action_assign_class_subject -> {
                            return true
                        }
                        R.id.action_delete_subject -> {
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
        return subjects.size
    }


}