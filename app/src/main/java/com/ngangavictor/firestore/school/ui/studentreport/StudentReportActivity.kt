package com.ngangavictor.firestore.school.ui.studentreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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

class StudentReportActivity : AppCompatActivity() {

    private lateinit var studentReportViewModel: StudentReportViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_report)

        studentReportViewModel = ViewModelProviders.of(this).get(StudentReportViewModel::class.java)

        textViewSchoolName=findViewById(R.id.textViewSchoolName)
        textViewAddress=findViewById(R.id.textViewAddress)
        textViewName=findViewById(R.id.textViewName)
        textViewClass=findViewById(R.id.textViewClass)
        textViewTotal=findViewById(R.id.textViewTotal)
        textViewMean=findViewById(R.id.textViewMean)
        textViewMeanGrade=findViewById(R.id.textViewMeanGrade)

        database= Firebase.firestore
        auth= Firebase.auth

        recyclerViewStudentReport=findViewById(R.id.recyclerViewStudentReport)

        recyclerViewStudentReport.layoutManager = LinearLayoutManager(this)
        recyclerViewStudentReport.setHasFixedSize(true)

        studentReportViewModel.schoolName.observe(this, Observer {
            textViewSchoolName.text = it
        })

        studentReportViewModel.schoolAddress.observe(this, Observer {
            textViewAddress.text = it
        })

        database.collection("schools").document(auth.currentUser!!.uid)
            .collection("results")/**.document("F8QKJPt0ayv0z269mXD1").collection("English").**/.
            addSnapshotListener { value, error ->
                for (i in value!!){
                    Log.e("STUDENT RESULTS", i.id)
                }
            }

    }
}