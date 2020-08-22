package com.ngangavictor.firestore.dialogs

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.models.ClassModel

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

    private lateinit var classList:List<String>

    private lateinit var spinnerAdapter:ArrayAdapter<String>

    private lateinit var alert: AlertDialog

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

        classList=ArrayList()

        spinnerAdapter= ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,classList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerClass.adapter=spinnerAdapter

        textViewAdd.setOnClickListener {
            saveExam()
        }

        textViewCancel.setOnClickListener {
            dialog!!.dismiss()
        }

        database.collection("classes").document(auth.currentUser!!.uid)
            .addSnapshotListener { value, error ->
                for (i in value!!.data!!.values) {
                    (classList as ArrayList<String>).add(i.toString())
                }

                spinnerAdapter.notifyDataSetChanged()
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
            loadingAlert()
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
                        alert.cancel()
                        dialog!!.dismiss()
                        Snackbar.make(requireView(), "Exam added", Snackbar.LENGTH_LONG).show()
                    } else {
                        alert.cancel()
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

    private fun loadingAlert(){
        val progressBar=ProgressBar(requireContext())

        val loadALert=AlertDialog.Builder(requireContext())
        loadALert.setCancelable(false)
        loadALert.setView(progressBar)
        alert=loadALert.create()
        alert.show()
    }

    fun newInstance(): AddExamDialog {
        return AddExamDialog()
    }

}