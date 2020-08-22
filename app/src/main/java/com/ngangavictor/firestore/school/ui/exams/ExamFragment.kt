package com.ngangavictor.firestore.school.ui.exams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.adapter.ExamAdapter
import com.ngangavictor.firestore.dialogs.AddExamDialog
import com.ngangavictor.firestore.listeners.ListenerAddExam
import com.ngangavictor.firestore.models.ExamModel

class ExamFragment : Fragment(), ListenerAddExam {

    private lateinit var examViewModel: ExamViewModel

    private lateinit var root: View

    private lateinit var recyclerViewExams: RecyclerView

    private lateinit var fabExam: FloatingActionButton

    private lateinit var examList: MutableList<ExamModel>

    private lateinit var examAdapter: ExamAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        examViewModel =
            ViewModelProviders.of(this).get(ExamViewModel::class.java)

        root = inflater.inflate(R.layout.fragment_exam, container, false)

        recyclerViewExams = root.findViewById(R.id.recyclerViewExams)

        fabExam = root.findViewById(R.id.fabExam)

        recyclerViewExams.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerViewExams.setHasFixedSize(true)

        examList = ArrayList()

        examViewModel.getExamData().observe(viewLifecycleOwner, Observer {
            examList = it as MutableList<ExamModel>

            examAdapter = ExamAdapter(
                examList as ArrayList<ExamModel>
            )

            examAdapter.notifyDataSetChanged()

            recyclerViewExams.adapter = examAdapter
        })


        fabExam.setOnClickListener {
            val addExamDialog = AddExamDialog(this).newInstance()
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

    override fun message(message: String) {
        if (message == "success") {
            Snackbar.make(requireView(), "Exam created.", Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(requireView(), "Error while creating. Try again", Snackbar.LENGTH_LONG)
                .show()
        }
    }
}