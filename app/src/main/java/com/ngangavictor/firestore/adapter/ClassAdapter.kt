package com.ngangavictor.firestore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ClassHolder
import com.ngangavictor.firestore.models.ClassModel

class ClassAdapter(private val classes: ArrayList<ClassModel>) :
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

        }
    }

    override fun getItemCount(): Int {
        return classes.size
    }
}