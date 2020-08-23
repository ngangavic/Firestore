package com.ngangavictor.firestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ClassHolder
import com.ngangavictor.firestore.listeners.ListenerDeleteClass
import com.ngangavictor.firestore.models.ClassModel

class ClassAdapter(
    private val context: Context,
    private val classes: ArrayList<ClassModel>,
    val listenerDeleteClass: ListenerDeleteClass
) :
    RecyclerView.Adapter<ClassHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassHolder {
        val viewHolder: ClassHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_class, parent, false)
        viewHolder = ClassHolder(layoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ClassHolder, position: Int) {
        holder.textViewName.text = classes[position].className
        holder.imageViewMore.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.imageViewMore)
            popupMenu.inflate(R.menu.class_menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {
                        R.id.action_delete_class -> {
                            listenerDeleteClass.className(classes[position].className)
                            return true
                        }
                        R.id.action_edit_class -> {
                            return true
                        }
                        R.id.action_enter_class_results->{

                        }
                        R.id.action_view_class_results->{

                        }
                    }
                    return false
                }

            })
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return classes.size
    }
}