package com.ngangavictor.firestore.dialogs

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R

class AddExamDialog : DialogFragment() {


    private lateinit var root: View

    private lateinit var editTextExamName: EditText
    private lateinit var editTextExamTerm: EditText
    private lateinit var editTextExamYear: EditText

    private lateinit var spinnerClass: Spinner

    private lateinit var textViewAdd: TextView
    private lateinit var textViewCancel: TextView

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.dialog_add_exam, container, false)

        editTextExamName = root.findViewById(R.id.editTextExamName)
        editTextExamTerm = root.findViewById(R.id.editTextExamTerm)
        editTextExamYear = root.findViewById(R.id.editTextExamYear)

        spinnerClass = root.findViewById(R.id.spinnerClass)

        textViewAdd = root.findViewById(R.id.textViewAdd)
        textViewCancel = root.findViewById(R.id.textViewCancel)

        database = Firebase.firestore
        auth = Firebase.auth

        textViewAdd.setOnClickListener {
            saveExam()
        }

        textViewCancel.setOnClickListener {
            dialog!!.dismiss()
        }

        return root
    }

    private fun saveExam() {
        val examName = editTextExamName.text.toString()
        val examTerm = editTextExamTerm.text.toString()
        val examYear = editTextExamYear.text.toString()
        val selectedClass = spinnerClass.selectedItem.toString()

        if (TextUtils.isEmpty(examName)) {
            editTextExamName.requestFocus()
            editTextExamName.error = "Cannot be empty"
        } else if (TextUtils.isEmpty(examTerm)) {
            editTextExamTerm.requestFocus()
            editTextExamTerm.error = "Cannot be empty"
        } else if (TextUtils.isEmpty(examYear)) {
            editTextExamYear.requestFocus()
            editTextExamYear.error = "Cannot be empty"
        } else if (selectedClass == "") {
            spinnerClass.performClick()
        } else {
            val exam = hashMapOf(
                "examName" to examName,
                "examTerm" to examTerm,
                "examYear" to examYear,
                "class" to selectedClass
            )
            database.collection("schools").document(auth.currentUser!!.uid).collection("exams")
                .document().set(exam, SetOptions.merge())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        dialog!!.dismiss()
                        Snackbar.make(requireView(), "Exam added", Snackbar.LENGTH_LONG).show()
                    } else {
                        dialog!!.dismiss()
                        Snackbar.make(
                            requireView(),
                            "Exam not added. Try again.",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    fun newInstance(): AddExamDialog {
        return AddExamDialog()
    }

}