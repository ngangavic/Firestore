package com.ngangavictor.firestore.school.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.firestore.R

class StudentReportFragment : Fragment() {

    companion object {
        fun newInstance() = StudentReportFragment()
    }

    private lateinit var viewModel: StudentReportViewModel

    private lateinit var root:View

    private lateinit var textViewSchoolName:TextView
    private lateinit var textViewAddress:TextView
    private lateinit var textViewName:TextView
    private lateinit var textViewClass:TextView
    private lateinit var textViewTotal:TextView
    private lateinit var textViewMean:TextView
    private lateinit var textViewMeanGrade:TextView

    private lateinit var recyclerViewStudentReport:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(StudentReportViewModel::class.java)

        root= inflater.inflate(R.layout.student_report_fragment, container, false)

        textViewSchoolName=root.findViewById(R.id.textViewSchoolName)
        textViewAddress=root.findViewById(R.id.textViewAddress)
        textViewName=root.findViewById(R.id.textViewName)
        textViewClass=root.findViewById(R.id.textViewClass)
        textViewTotal=root.findViewById(R.id.textViewTotal)
        textViewMean=root.findViewById(R.id.textViewMean)
        textViewMeanGrade=root.findViewById(R.id.textViewMeanGrade)

        recyclerViewStudentReport=root.findViewById(R.id.recyclerViewStudentReport)

        recyclerViewStudentReport.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewStudentReport.setHasFixedSize(true)

        return root
    }

}