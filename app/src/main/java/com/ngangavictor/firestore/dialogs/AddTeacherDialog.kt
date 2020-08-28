package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.ngangavictor.firestore.R

class AddTeacherDialog:DialogFragment() {

    lateinit var root:View

    private lateinit var editTextName:EditText
    private lateinit var editTextPhone:EditText
    private lateinit var editTextEmail:EditText

    private lateinit var buttonAdd:Button
    private lateinit var buttonCancel:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root=inflater.inflate(R.layout.dialog_add_teacher,container,false)

        editTextName=root.findViewById(R.id.editTextName)
        editTextPhone=root.findViewById(R.id.editTextPhone)
        editTextEmail=root.findViewById(R.id.editTextEmail)

        buttonAdd=root.findViewById(R.id.buttonAdd)
        buttonCancel=root.findViewById(R.id.buttonCancel)

        return root
    }

}