package com.ngangavictor.firestore.school.ui.teachers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngangavictor.firestore.R

class TeacherFragment : Fragment() {

    companion object {
        fun newInstance() = TeacherFragment()
    }

    private lateinit var viewModel: TeacherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.teacher_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TeacherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}