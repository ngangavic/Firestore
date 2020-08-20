package com.ngangavictor.firestore.repos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRepository {

    private val database:FirebaseFirestore=Firebase.firestore
    private val auth:FirebaseAuth=Firebase.auth

    fun getClasses():DocumentReference{
      return  database.collection("classes").document(auth.currentUser!!.uid)
    }


}