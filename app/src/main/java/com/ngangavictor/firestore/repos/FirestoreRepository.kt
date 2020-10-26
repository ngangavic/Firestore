package com.ngangavictor.firestore.repos

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepository {

    private val database: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    fun getClasses(): DocumentReference {
        return database.collection("classes").document(auth.currentUser!!.uid)
    }

    fun getExams(): CollectionReference {
        return database.collection("schools").document(auth.currentUser!!.uid).collection("exams")
    }

    fun getSubject(): CollectionReference {
        return database.collection("schools").document(auth.currentUser!!.uid)
            .collection("subjects")
    }

    fun getStudents(className: String): Query {
        return database.collection("schools").document(auth.currentUser!!.uid)
            .collection("students")
            .document("classes").collection(className)
    }

    fun getResults(examId: String, subjectName: String): CollectionReference {
        return database.collection("schools").document(auth.currentUser!!.uid)
            .collection("results").document(examId).collection(subjectName)
    }

    fun getGrade(selectedClass: String, selectedSubject: String): CollectionReference {
        return database.collection("schools").document(auth.currentUser!!.uid).collection("grades")
            .document(selectedClass)
            .collection(selectedSubject)
    }

    fun getSchoolDetails(): DocumentReference {
        return database.collection("schools").document(auth.currentUser!!.uid)
    }

    fun getStudentResults(examId: String,subjectName:String): CollectionReference {
        Log.e("Exam ID",examId)
        Log.e("Exam Subject",subjectName)
        return database.collection("schools").document(auth.currentUser!!.uid)
            .collection("results").document(examId).collection(subjectName)
//        return database.collection("schools").document(auth.currentUser!!.uid).collection("results").document("F8QKJPt0ayv0z269mXD1")//.document(examId)
    }

}