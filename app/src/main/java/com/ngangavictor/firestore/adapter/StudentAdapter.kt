package com.ngangavictor.firestore.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.dialogs.AddExamDialog
import com.ngangavictor.firestore.dialogs.StudentReportDialog
import com.ngangavictor.firestore.holder.StudentHolder
import com.ngangavictor.firestore.models.StudentModel
import com.ngangavictor.firestore.school.SchoolActivity
import com.ngangavictor.firestore.school.ui.StudentReportFragment
import com.ngangavictor.firestore.school.ui.studentreport.StudentReportActivity

class StudentAdapter(
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
        holder.textViewAdm.text = students[position].adm
        holder.textViewName.text = students[position].name
        holder.textViewParentNo.text = students[position].parentNo
        holder.textViewKCPE.text = students[position].kcpe
        holder.imageViewMore.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.imageViewMore)
            popupMenu.inflate(R.menu.student_menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {
                        R.id.action_edit_student -> {
                            return true
                        }
                        R.id.action_delete_student -> {
                            return true
                        }
                        R.id.action_view_results_student -> {

                            val ac =context as SchoolActivity
//                            val studentReportDialog = StudentReportDialog().newInstance()
////                            addExamDialog.isCancelable = false
//                            ac.supportFragmentManager.let {
//                                studentReportDialog.show(
//                                    it,
//                                    "dialog student report"
//                                )
//                            }
//
                            context.startActivity(Intent(ac,StudentReportActivity::class.java))
                            context.finish()
//                            val studentReportFragment=StudentReportFragment()
//                            val bundle=Bundle()
//                            bundle.putString("studentName",students[position].name)
//                            bundle.putString("studentClass","Form 1")
//                            studentReportFragment.arguments=bundle
//                            val ctx=context as SchoolActivity
//                            ctx.supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment,studentReportFragment).commit()
                            return true
                        }
                        R.id.action_sms_parent_student -> {
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
        return students.size
    }
}