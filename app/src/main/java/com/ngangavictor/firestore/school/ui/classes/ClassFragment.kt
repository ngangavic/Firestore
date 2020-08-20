package com.ngangavictor.firestore.school.ui.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ngangavictor.firestore.R

class ClassFragment : Fragment() {

    private lateinit var classViewModel: ClassViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classViewModel =
            ViewModelProviders.of(this).get(ClassViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_class, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        classViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}