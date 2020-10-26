package com.ngangavictor.firestore.school.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.models.ResultModel
import com.ngangavictor.firestore.models.StudentModel
import com.ngangavictor.firestore.models.StudentReportModel
import com.ngangavictor.firestore.repos.FirestoreRepository

class StudentReportViewModel : ViewModel() {

    private val firestoreRepository=FirestoreRepository()

    private var studentReportList: MutableList<StudentReportModel> = mutableListOf()

    private val studentReportData = MutableLiveData<List<StudentReportModel>>()

    private val _schoolName = MutableLiveData<String>().apply {
        firestoreRepository.getSchoolDetails().get().addOnCompleteListener {
            if (it.isSuccessful){
               value= it.result!!.get("schoolName") as String
            }
        }
    }
    val schoolName: LiveData<String> = _schoolName

    private val _schoolAddress = MutableLiveData<String>().apply {
        firestoreRepository.getSchoolDetails().get().addOnCompleteListener {
            if (it.isSuccessful){
               value=it.result?.get("schoolAddress") as String
            }
        }
    }
    val schoolAddress: LiveData<String> = _schoolAddress

    fun getStudentResultData(examId:String,subjectName:String): MutableLiveData<List<StudentReportModel>> {

        firestoreRepository.getResults(examId,subjectName).
                addSnapshotListener { value, error ->
                    for (i in value!!){
                        Log.e("STUDENT RESULTS", i.id)
                    }
                }

        return studentReportData
    }

}