package com.ngangavictor.firestore.school.ui.teachers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngangavictor.firestore.R

class TeacherFragment : Fragment() {

    private lateinit var viewModel: TeacherViewModel

    private lateinit var root: View

    private lateinit var recyclerViewTeachers: RecyclerView

    private lateinit var fabAddTeacher: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.teacher_fragment, container, false)

        recyclerViewTeachers = root.findViewById(R.id.recyclerViewTeachers)

        fabAddTeacher = root.findViewById(R.id.fabAddTeacher)

        viewModel = ViewModelProviders.of(this).get(TeacherViewModel::class.java)

        return root
    }

}