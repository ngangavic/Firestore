package com.ngangavictor.firestore.school.ui.students

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.dialogs.AddExamDialog
import com.ngangavictor.firestore.dialogs.AddStudentDialog

class StudentFragment : Fragment() {

    lateinit var fabAddStudent:FloatingActionButton

    lateinit var root:View

    lateinit var recyclerViewStudents:RecyclerView

    lateinit var spinnerClass:Spinner

    companion object {
        fun newInstance() = StudentFragment()
    }

    private lateinit var viewModel: StudentViewModel

    private lateinit var classList: List<String>

    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private lateinit var alert: AlertDialog

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root=inflater.inflate(R.layout.student_fragment, container, false)

        fabAddStudent=root.findViewById(R.id.fabAddStudent)
        recyclerViewStudents=root.findViewById(R.id.recyclerViewStudents)
        spinnerClass=root.findViewById(R.id.spinnerClass)

        database = Firebase.firestore
        auth = Firebase.auth

        classList = ArrayList()

        spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, classList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerClass.adapter = spinnerAdapter

        database.collection("classes").document(auth.currentUser!!.uid)
            .addSnapshotListener { value, error ->
                for (i in value!!.data!!.values) {
                    (classList as ArrayList<String>).add(i.toString())
                }

                spinnerAdapter.notifyDataSetChanged()
            }

        fabAddStudent.setOnClickListener {
            val addStudentDialog = AddStudentDialog().newInstance()
            addStudentDialog.isCancelable = false
            requireActivity().supportFragmentManager.let {
                addStudentDialog.show(
                    it,
                    "dialog add student"
                )
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}