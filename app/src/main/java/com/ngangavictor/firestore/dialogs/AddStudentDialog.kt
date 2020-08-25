package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ngangavictor.firestore.R
import kotlinx.android.synthetic.main.student_fragment.view.*

class AddStudentDialog:DialogFragment() {

    lateinit var root:View

    private lateinit var textViewData:TextView
    private lateinit var textViewMessage:TextView

    private lateinit var buttonUpload:Button
    private lateinit var buttonSelectFile:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root=inflater.inflate(R.layout.dialog_add_student,container,false)

        textViewData=root.findViewById(R.id.textViewData)
        textViewMessage=root.findViewById(R.id.textViewMessage)

        buttonUpload=root.findViewById(R.id.buttonUpload)
        buttonSelectFile=root.findViewById(R.id.buttonSelectFile)

        return root
    }
}