package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.school.ui.StudentReportViewModel

class StudentReportDialog : DialogFragment() {

    private lateinit var studentReportViewModel: StudentReportViewModel

    private lateinit var root: View

    private lateinit var textViewSchoolName: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var textViewName: TextView
    private lateinit var textViewClass: TextView
    private lateinit var textViewTotal: TextView
    private lateinit var textViewMean: TextView
    private lateinit var textViewMeanGrade: TextView

    private lateinit var recyclerViewStudentReport: RecyclerView

    lateinit var database: FirebaseFirestore

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        studentReportViewModel = ViewModelProviders.of(this).get(StudentReportViewModel::class.java)

        root = inflater.inflate(R.layout.student_report_fragment, container, false)

        textViewSchoolName = root.findViewById(R.id.textViewSchoolName)
        textViewAddress = root.findViewById(R.id.textViewAddress)
        textViewName = root.findViewById(R.id.textViewName)
        textViewClass = root.findViewById(R.id.textViewClass)
        textViewTotal = root.findViewById(R.id.textViewTotal)
        textViewMean = root.findViewById(R.id.textViewMean)
        textViewMeanGrade = root.findViewById(R.id.textViewMeanGrade)

        database = Firebase.firestore
        auth = Firebase.auth

        recyclerViewStudentReport = root.findViewById(R.id.recyclerViewStudentReport)

        recyclerViewStudentReport.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewStudentReport.setHasFixedSize(true)

        studentReportViewModel.schoolName.observe(viewLifecycleOwner, Observer {
            textViewSchoolName.text = it
        })

        studentReportViewModel.schoolAddress.observe(viewLifecycleOwner, Observer {
            textViewAddress.text = it
        })

        database.collection("schools").document(auth.currentUser!!.uid)
            .collection("results")
            /**.document("F8QKJPt0ayv0z269mXD1").collection("English").**/
            .addSnapshotListener { value, error ->
                for (i in value!!) {
                    Log.e("STUDENT RESULTS", i.id)
                }
            }

//        studentReportViewModel.getStudentResultData("F8QKJPt0ayv0z269mXD1","English").observe(viewLifecycleOwner, Observer {

//            classList = it as MutableList<ClassModel>
//
//            classAdapter = ClassAdapter(
//                requireContext(), classList as ArrayList<ClassModel>, this
//            )
//
//            classAdapter.notifyDataSetChanged()
//
//            recyclerViewClasses.adapter = classAdapter
//        })


        return root
    }

    fun newInstance(): StudentReportDialog {
        return StudentReportDialog()
    }

}