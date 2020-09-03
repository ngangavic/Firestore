package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.ngangavictor.firestore.R

class AddGradeDialog:DialogFragment() {

    lateinit var root:View

    lateinit var spinnerClass:Spinner
    lateinit var spinnerSubject:Spinner

    lateinit var editTextGrade:EditText
    lateinit var editTextStartRange:EditText
    lateinit var editTextEndRange:EditText

    lateinit var buttonCancel:Button
    lateinit var buttonAdd:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root=inflater.inflate(R.layout.dialog_add_grade,container,false)

        spinnerSubject=root.findViewById(R.id.spinnerSubject)
        spinnerClass=root.findViewById(R.id.spinnerClass)

        editTextGrade=root.findViewById(R.id.editTextGrade)
        editTextStartRange=root.findViewById(R.id.editTextStartRange)
        editTextEndRange=root.findViewById(R.id.editTextEndRange)

        buttonCancel=root.findViewById(R.id.buttonCancel)
        buttonAdd=root.findViewById(R.id.buttonAdd)

        return root
    }
}