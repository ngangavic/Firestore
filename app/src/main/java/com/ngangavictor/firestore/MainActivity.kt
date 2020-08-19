package com.ngangavictor.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var editTextFName: EditText
    private lateinit var editTextLName: EditText
    private lateinit var editTextDate: EditText
    private lateinit var buttonAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firestoreDb = Firebase.firestore
        editTextFName = findViewById(R.id.editTextFName)
        editTextLName = findViewById(R.id.editTextLName)
        editTextDate = findViewById(R.id.editTextDate)
        buttonAdd = findViewById(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            addData()
        }
    }

    private fun addData() {
        val fName = editTextFName.text.toString()
        val lName = editTextLName.text.toString()
        val date = editTextDate.text.toString()

        when {
            TextUtils.isEmpty(fName) -> {
                editTextFName.requestFocus()
                editTextFName.error = "Cannot be empty"
            }
            TextUtils.isEmpty(lName) -> {
                editTextLName.requestFocus()
                editTextLName.error = "Cannot be empty"
            }
            TextUtils.isEmpty(date) -> {
                editTextDate.requestFocus()
                editTextDate.error = "Cannot be empty"
            }
            else -> {
                val user = hashMapOf(
                        "first" to fName,
                        "last" to lName,
                        "date" to date
                )
                firestoreDb.collection("users").add(user)
                        .addOnFailureListener {
                            Snackbar.make(findViewById(android.R.id.content), "Error: " + it.message, Snackbar.LENGTH_LONG).show()
                        }
                        .addOnSuccessListener {
                            clearText()
                            Snackbar.make(findViewById(android.R.id.content), "Success: " + it.id, Snackbar.LENGTH_LONG).show()
                        }
            }
        }

    }

    private fun clearText(){
        editTextFName.text.clear()
        editTextLName.text.clear()
        editTextDate.text.clear()
    }

}