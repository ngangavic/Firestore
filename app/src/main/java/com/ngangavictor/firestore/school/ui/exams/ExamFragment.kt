package com.ngangavictor.firestore.school.ui.exams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.dialogs.AddExamDialog

class ExamFragment : Fragment() {

    private lateinit var examViewModel: ExamViewModel

    private lateinit var root:View

    private lateinit var recyclerViewExams:RecyclerView

    private lateinit var fabExam:FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        examViewModel =
            ViewModelProviders.of(this).get(ExamViewModel::class.java)

        root = inflater.inflate(R.layout.fragment_exam, container, false)

        examViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        recyclerViewExams=root.findViewById(R.id.recyclerViewExams)

        fabExam=root.findViewById(R.id.fabExam)

        fabExam.setOnClickListener {
            val addExamDialog = AddExamDialog().newInstance()
            addExamDialog.isCancelable = false
            requireActivity().supportFragmentManager.let {
                addExamDialog.show(
                    it,
                    "dialog add exam"
                )
            }
        }

        return root
    }
}