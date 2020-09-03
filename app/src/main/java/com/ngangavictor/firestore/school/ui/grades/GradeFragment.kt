package com.ngangavictor.firestore.school.ui.grades

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.dialogs.AddGradeDialog
import com.ngangavictor.firestore.listeners.ListenerGrade

class GradeFragment : Fragment(), ListenerGrade {

    private lateinit var root: View

    private lateinit var gradeViewModel: GradeViewModel

    private lateinit var spinnerClass: Spinner
    private lateinit var spinnerSubject: Spinner

    private lateinit var recyclerViewGrade: RecyclerView

    private lateinit var fabEditGrade: FloatingActionButton
    private lateinit var fabAddGrade: FloatingActionButton

    lateinit var database: FirebaseFirestore

    lateinit var auth: FirebaseAuth

    private lateinit var classList: List<String>
    private lateinit var subjectList: List<String>

    private lateinit var spinnerClassAdapter: ArrayAdapter<String>
    private lateinit var spinnerSubjectAdapter: ArrayAdapter<String>

    private lateinit var selectedClass: String
    private lateinit var selectedSubject: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.grade_fragment, container, false)

        gradeViewModel =
            ViewModelProviders.of(this).get(GradeViewModel::class.java)

        spinnerClass = root.findViewById(R.id.spinnerClass)
        spinnerSubject = root.findViewById(R.id.spinnerSubject)

        recyclerViewGrade = root.findViewById(R.id.recyclerViewGrade)

        fabEditGrade = root.findViewById(R.id.fabEditGrade)
        fabAddGrade = root.findViewById(R.id.fabAddGrade)

        database = Firebase.firestore
        auth = Firebase.auth

        classList = ArrayList()
        subjectList = ArrayList()

        (classList as ArrayList<String>).add("Select Class")
        (subjectList as ArrayList<String>).add("Select Subject")

        selectedSubject = "Select Subject"
        selectedClass = "Select Class"

        recyclerViewGrade.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewGrade.setHasFixedSize(true)

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
                for (i in value!!) {
                    (subjectList as ArrayList<String>).add(i.data.get("subjectName").toString())
                }
            }

        spinnerClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedClass = parent!!.getItemAtPosition(position).toString()

                if (selectedClass != "Select Class" && selectedSubject != "Select Subject") {
                    checkGrades(selectedClass, selectedSubject)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSubject = parent!!.getItemAtPosition(position).toString()

                if (selectedClass != "Select Class" && selectedSubject != "Select Subject") {
                    checkGrades(selectedClass, selectedSubject)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        fabAddGrade.setOnClickListener {
            val addGradeDialog = AddGradeDialog(this).newInstance()
            addGradeDialog.isCancelable = false
            requireActivity().supportFragmentManager.let {
                addGradeDialog.show(
                    it,
                    "dialog add grade"
                )
            }
        }


        return root
    }

    private fun checkGrades(selectedClass: String, selectedSubject: String) {
        database.collection("schools").document(auth.currentUser!!.uid).collection("grades")
            .document(selectedClass)
            .collection(selectedSubject).get().addOnCompleteListener {
                if (it.result!!.size() == 0) {
                    Log.e("ERROR", "No data")
                } else {
                    for (i in it.result!!.documents) {
                        Log.i("DATA", i.data!!.entries.toString())
                    }
                }
            }
    }

    override fun addGradeMessage(message: String, selectedClass: String, selectedSubject: String) {
        val view = requireView()
        view.let { v ->
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
        if (message == "success") {
            spinnerClass.setSelection(classList.indexOf(selectedClass))
            spinnerSubject.setSelection(subjectList.indexOf(selectedSubject))
            Snackbar.make(requireView(), "Grade successfully added", Snackbar.LENGTH_LONG).show()
        } else {
            spinnerClass.setSelection(classList.indexOf(selectedClass))
            spinnerSubject.setSelection(subjectList.indexOf(selectedSubject))
            Snackbar.make(requireView(), "Error: $message", Snackbar.LENGTH_LONG).show()
        }
    }

}