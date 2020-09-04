package com.ngangavictor.firestore.school.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.repos.FirestoreRepository

class StudentReportViewModel : ViewModel() {

    val firestoreRepository=FirestoreRepository()

    private val _schoolName = MutableLiveData<String>().apply {
        value = firestoreRepository.getSchoolDetails().get().result?.get("schoolName") as String
    }
    val schoolName: LiveData<String> = _schoolName

    private val _schoolAddress = MutableLiveData<String>().apply {
        value = firestoreRepository.getSchoolDetails().get().result?.get("schoolAddress") as String
    }
    val schoolAddress: LiveData<String> = _schoolAddress

}