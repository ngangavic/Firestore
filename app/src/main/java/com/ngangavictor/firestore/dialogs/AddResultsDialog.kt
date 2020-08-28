package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.ngangavictor.firestore.R

class AddResultsDialog:DialogFragment() {

    lateinit var root:View

    lateinit var spinnerExam:Spinner
    lateinit var spinnerClass:Spinner
    lateinit var spinnerSubject:Spinner

    lateinit var buttonCancel:Button
    lateinit var buttonAdd:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root=layoutInflater.inflate(R.layout.dialog_add_result,container,false)

        spinnerSubject=root.findViewById(R.id.spinnerSubject)
        spinnerClass=root.findViewById(R.id.spinnerClass)
        spinnerExam=root.findViewById(R.id.spinnerExam)

        buttonCancel=root.findViewById(R.id.buttonCancel)
        buttonAdd=root.findViewById(R.id.buttonAdd)

        return root
    }
}