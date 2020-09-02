package com.ngangavictor.firestore.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.holder.ResultHolder
import com.ngangavictor.firestore.listeners.ListenerResult
import com.ngangavictor.firestore.models.ResultModel

class ResultAdapter(
    private val context: Context,
    private val results: ArrayList<ResultModel>,
private val listenerResult: ListenerResult) :
    RecyclerView.Adapter<ResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val viewHolder: ResultHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_result, parent, false)
        viewHolder = ResultHolder(layoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.textViewAdm.text = "Adm: " + results[position].adm
        holder.editTextMarks.setText(results[position].marks)

        holder.editTextMarks.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.isNullOrEmpty()) {

                }else{
                if (s.length>=2){
                    //save
                    listenerResult.saveMarks(
                        holder.editTextMarks.text.toString().toInt(),
                        results[position].adm
                    )
                }else{
                    listenerResult.error("Error: Input two characters")
                }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    override fun getItemCount(): Int {
        return results.size
    }

}