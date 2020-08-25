package com.ngangavictor.firestore.school.ui.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngangavictor.firestore.R

class StudentFragment : Fragment() {

    lateinit var fabAddStudent:FloatingActionButton

    lateinit var root:View

    lateinit var recyclerViewStudents:RecyclerView

    lateinit var spinnerClass:Spinner

    companion object {
        fun newInstance() = StudentFragment()
    }

    private lateinit var viewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root=inflater.inflate(R.layout.student_fragment, container, false)

        fabAddStudent=root.findViewById(R.id.fabAddStudent)
        recyclerViewStudents=root.findViewById(R.id.recyclerViewStudents)
        spinnerClass=root.findViewById(R.id.spinnerClass)


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}