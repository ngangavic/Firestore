package com.ngangavictor.firestore.school.ui.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngangavictor.firestore.R

class SubjectFragment : Fragment() {

    private lateinit var subjectViewModel: SubjectViewModel

    private lateinit var root:View

    private lateinit var recyclerViewSubjects:RecyclerView

    private lateinit var fabAddSubject:FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        subjectViewModel =
            ViewModelProviders.of(this).get(SubjectViewModel::class.java)

        root = inflater.inflate(R.layout.fragment_subject, container, false)

        recyclerViewSubjects=root.findViewById(R.id.recyclerViewSubjects)

        fabAddSubject=root.findViewById(R.id.fabAddSubject)

        recyclerViewSubjects.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerViewSubjects.setHasFixedSize(true)

        subjectViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}