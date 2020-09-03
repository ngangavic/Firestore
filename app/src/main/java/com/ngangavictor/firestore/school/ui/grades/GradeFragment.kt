package com.ngangavictor.firestore.school.ui.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.adapter.StudentAdapter
import com.ngangavictor.firestore.models.StudentModel
import com.ngangavictor.firestore.school.ui.exams.ExamViewModel

class GradeFragment : Fragment() {

   private lateinit var root:View

    private lateinit var gradeViewModel: GradeViewModel

    private lateinit var spinnerClass:Spinner
    private lateinit var spinnerSubject:Spinner

    private lateinit var recyclerViewGrade:RecyclerView

    private lateinit var fabEditGrade:FloatingActionButton

    lateinit var database:FirebaseFirestore

    lateinit var auth: FirebaseAuth

    private lateinit var classList: List<String>
    private lateinit var subjectList: List<String>

    private lateinit var spinnerClassAdapter: ArrayAdapter<String>
    private lateinit var spinnerSubjectAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root= inflater.inflate(R.layout.grade_fragment, container, false)

        gradeViewModel =
            ViewModelProviders.of(this).get(GradeViewModel::class.java)

        spinnerClass=root.findViewById(R.id.spinnerClass)
        spinnerSubject=root.findViewById(R.id.spinnerSubject)

        recyclerViewGrade=root.findViewById(R.id.recyclerViewGrade)

        fabEditGrade=root.findViewById(R.id.fabEditGrade)

        database=Firebase.firestore
        auth=Firebase.auth

        classList=ArrayList()
        subjectList=ArrayList()

        (classList as ArrayList<String>).add("Select Class")
        (subjectList as ArrayList<String>).add("Select Subject")

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
                for (i in value!!){
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

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        return root
    }

}