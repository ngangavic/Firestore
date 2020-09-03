package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.text.TextUtils
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
import com.ngangavictor.firestore.listeners.ListenerGrade

class AddGradeDialog(private val listenerGrade: ListenerGrade):DialogFragment() {

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

        buttonAdd.setOnClickListener { addGrade() }

        buttonCancel.setOnClickListener { dismiss() }

        return root
    }

    private fun addGrade(){
        val grade=editTextGrade.text.toString()
        val start=editTextStartRange.text.toString()
        val end=editTextEndRange.text.toString()
        val selectedClass=spinnerClass.selectedItem.toString()
        val selectedSubject=spinnerSubject.selectedItem.toString()
        if (selectedClass=="Select Class"){
            spinnerClass.performClick()
        }else if (selectedSubject=="Select Subject"){
            spinnerSubject.performClick()
        }else if (TextUtils.isEmpty(grade)){
            editTextGrade.requestFocus()
            editTextGrade.error="Cannot be empty"
        }else if (TextUtils.isEmpty(start)){
editTextStartRange.requestFocus()
            editTextStartRange.error="Cannot be empty"
        }else if (TextUtils.isEmpty(end)){
editTextEndRange.requestFocus()
            editTextEndRange.error="Cannot be empty"
        }else{

            val gradeData= hashMapOf(
                "grade" to grade,
                "start" to start,
                "end" to end
            )

            database.collection("schools").document(auth.currentUser!!.uid).collection("grades").document(selectedClass)
                .collection(selectedSubject).add(gradeData)
                .addOnCompleteListener {
                    if (it.isSuccessful){
            dismiss()
                        listenerGrade.addGradeMessage("success",selectedClass,selectedSubject)
                    }else{
                     dismiss()
listenerGrade.addGradeMessage(it.exception!!.message.toString(),selectedClass,selectedSubject)
                    }
                }
        }
    }



    fun newInstance(): AddGradeDialog {
        return AddGradeDialog(listenerGrade)
    }
}