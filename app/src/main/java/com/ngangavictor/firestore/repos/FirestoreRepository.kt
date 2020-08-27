package com.ngangavictor.firestore.repos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.core.OrderBy
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.StructuredQuery

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

    fun getStudents(className:String): Query {
        return database.collection("schools").document(auth.currentUser!!.uid).collection("students")
            .document("classes").collection(className)
    }

}