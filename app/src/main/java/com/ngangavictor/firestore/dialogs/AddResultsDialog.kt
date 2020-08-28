package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R

class AddResultsDialog : DialogFragment() {

    lateinit var root: View

    lateinit var spinnerExam: Spinner
    lateinit var spinnerClass: Spinner
    lateinit var spinnerSubject: Spinner

    lateinit var buttonCancel: Button
    lateinit var buttonAdd: Button

    lateinit var database: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    lateinit var spinnerExamAdapter: ArrayAdapter<String>
    lateinit var spinnerSubjectAdapter: ArrayAdapter<String>

    private lateinit var examList: List<String>
    private lateinit var classList: List<String>
    private lateinit var subjectList: List<String>
    private lateinit var examKeyList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = layoutInflater.inflate(R.layout.dialog_add_result, container, false)

        spinnerSubject = root.findViewById(R.id.spinnerSubject)
        spinnerExam = root.findViewById(R.id.spinnerExam)

        buttonCancel = root.findViewById(R.id.buttonCancel)
        buttonAdd = root.findViewById(R.id.buttonAdd)

        database = Firebase.firestore
        auth = Firebase.auth

        classList = ArrayList()
        examList = ArrayList()
        subjectList = ArrayList()
        examKeyList = ArrayList()

        (classList as ArrayList<String>).add("Select Class")
        (examList as ArrayList<String>).add("Select Exam")
        (subjectList as ArrayList<String>).add("Select Subject")
        (examKeyList as ArrayList<String>).add("Select Key")

        spinnerExamAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, examList)
        spinnerExamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerExam.adapter = spinnerExamAdapter


        spinnerSubjectAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subjectList)
        spinnerSubjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSubject.adapter = spinnerSubjectAdapter


        database.collection("schools").document(auth.currentUser!!.uid).collection("subjects")
            .addSnapshotListener { value, error ->
                for (i in value!!) {
                    (subjectList as ArrayList<String>).add(i.data.get("subjectName").toString())
                }

                spinnerSubjectAdapter.notifyDataSetChanged()
            }

        database.collection("schools").document(auth.currentUser!!.uid).collection("exams")
            .addSnapshotListener { value, error ->
                for (i in value!!) {
                    (examKeyList as ArrayList<String>).add(i.id)

                    (classList as ArrayList<String>).add(i.data.get("class").toString())

                    (examList as ArrayList<String>).add(
                        i.data.get("class").toString() +" "+ i.data.get(
                            "examName"
                        ).toString() +" "+ i.data.get("examTerm").toString() +" "+ i.data.get("examYear")
                            .toString()
                    )
                }

                spinnerExamAdapter.notifyDataSetChanged()
            }

        spinnerExam.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.e("SELECTED EXAM",parent!!.getItemAtPosition(position).toString())
                Log.e("SELECTED CLASS",classList.get(position))
                Log.e("SELECTED EXAM KEY",examKeyList.get(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })

        return root
    }

    private fun addExam() {
        if (spinnerClass.selectedItem.toString() == "Select Class") {
            spinnerClass.performClick()
        } else if (spinnerExam.selectedItem.toString() == "Select Exam") {
            spinnerExam.performClick()
        } else if (spinnerSubject.selectedItem.toString() == "Select Subject") {
            spinnerSubject.performClick()
        } else {

        }
    }

    fun newInstance(): AddResultsDialog {
        return AddResultsDialog()
    }

}