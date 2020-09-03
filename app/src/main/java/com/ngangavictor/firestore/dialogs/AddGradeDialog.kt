package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R

class AddGradeDialog:DialogFragment() {

    lateinit var root:View

    private lateinit var spinnerClass:Spinner
    private lateinit var spinnerSubject:Spinner

    private lateinit var editTextGrade:EditText
    private lateinit var editTextStartRange:EditText
    private lateinit var editTextEndRange:EditText

   private lateinit var buttonCancel:Button
    private lateinit var buttonAdd:Button

    lateinit var database:FirebaseFirestore

    lateinit var auth: FirebaseAuth

    private lateinit var classList: List<String>
    private lateinit var subjectList: List<String>

    private lateinit var spinnerClassAdapter: ArrayAdapter<String>
    private lateinit var spinnerSubjectAdapter: ArrayAdapter<String>

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

        database=Firebase.firestore
        auth=Firebase.auth

        classList=ArrayList()
        subjectList=ArrayList()

        (classList as ArrayList<String>).add("Select Class")
        (subjectList as ArrayList<String>).add("Select Subject")

        spinnerClassAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, classList)
        spinnerClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerClass.adapter = spinnerClassAdapter

        spinnerSubjectAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subjectList)
        spinnerSubjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSubject.adapter = spinnerSubjectAdapter

        database.collection("classes").document(auth.currentUser!!.uid)
            .addSnapshotListener { value, error ->
                for (i in value!!.data!!.values) {
                    (classList as ArrayList<String>).add(i.toString())
                }

                spinnerClassAdapter.notifyDataSetChanged()

            }

        database.collection("schools").document(auth.currentUser!!.uid).collection("subjects")
            .addSnapshotListener { value, error ->
                for (i in value!!){
                    (subjectList as ArrayList<String>).add(i.data.get("subjectName").toString())
                }
            }

        return root
    }



    fun newInstance(): AddGradeDialog {
        return AddGradeDialog()
    }
}