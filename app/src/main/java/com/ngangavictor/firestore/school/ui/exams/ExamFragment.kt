package com.ngangavictor.firestore.school.ui.exams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ngangavictor.firestore.R

class ExamFragment : Fragment() {

    private lateinit var examViewModel: ExamViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        examViewModel =
            ViewModelProviders.of(this).get(ExamViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_exam, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        examViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}