package com.ngangavictor.firestore.school.ui.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ngangavictor.firestore.R

class SubjectFragment : Fragment() {

    private lateinit var subjectViewModel: SubjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        subjectViewModel =
            ViewModelProviders.of(this).get(SubjectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_subject, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        subjectViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}