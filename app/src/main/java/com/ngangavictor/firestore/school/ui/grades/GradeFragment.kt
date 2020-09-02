package com.ngangavictor.firestore.school.ui.grades

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
import com.ngangavictor.firestore.school.ui.exams.ExamViewModel

class GradeFragment : Fragment() {

   private lateinit var root:View

    private lateinit var gradeViewModel: GradeViewModel

    private lateinit var spinnerClass:Spinner

    private lateinit var recyclerViewGrade:RecyclerView

    private lateinit var fabEditGrade:FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root= inflater.inflate(R.layout.grade_fragment, container, false)

        gradeViewModel =
            ViewModelProviders.of(this).get(GradeViewModel::class.java)

        spinnerClass=root.findViewById(R.id.spinnerClass)

        recyclerViewGrade=root.findViewById(R.id.recyclerViewGrade)

        fabEditGrade=root.findViewById(R.id.fabEditGrade)

        return root
    }

}