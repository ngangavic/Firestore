package com.ngangavictor.firestore.school.ui.grades

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngangavictor.firestore.R

class GradeFragment : Fragment() {

    companion object {
        fun newInstance() = GradeFragment()
    }

    private lateinit var viewModel: GradeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grade_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GradeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}