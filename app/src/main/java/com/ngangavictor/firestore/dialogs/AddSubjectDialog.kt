package com.ngangavictor.firestore.dialogs

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.listeners.ListenerSubject

class AddSubjectDialog(private val listenerSubject: ListenerSubject) : DialogFragment() {

    private lateinit var root: View

    private lateinit var editTextSubjectName: EditText

    private lateinit var textViewCancel: TextView
    private lateinit var textViewAdd: TextView

    private lateinit var alert: AlertDialog

    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.dialog_add_subject, container, false)

        editTextSubjectName = root.findViewById(R.id.editTextSubjectName)

        textViewCancel = root.findViewById(R.id.textViewCancel)
        textViewAdd = root.findViewById(R.id.textViewAdd)

        auth = Firebase.auth
        database = Firebase.firestore

        textViewAdd.setOnClickListener {
            addSubject()
        }

        textViewCancel.setOnClickListener {
            dismiss()
        }

        return root
    }

    private fun addSubject() {
        val subjectName = editTextSubjectName.text.toString()

        if (TextUtils.isEmpty(subjectName)) {
            editTextSubjectName.requestFocus()
            editTextSubjectName.error = "Cannot be empty"
        } else {

            loadingAlert()
            val subject = hashMapOf(
                "subjectName" to subjectName
            )

            database.collection("schools").document(auth.currentUser!!.uid).collection("subjects")
                .document().set(subject, SetOptions.merge())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        alert.cancel()
                        dialog!!.dismiss()
                        listenerSubject.addSubjectResponse("success")
                    } else {
                        alert.cancel()
                        dialog!!.dismiss()
                        listenerSubject.addSubjectResponse("failed")
                    }
                }
        }

    }

    private fun loadingAlert() {
        val progressBar = ProgressBar(requireContext())

        val loadAlert = AlertDialog.Builder(requireContext())
        loadAlert.setCancelable(false)
        loadAlert.setView(progressBar)
        alert = loadAlert.create()
        alert.show()
    }

    fun newInstance(): AddSubjectDialog {
        return AddSubjectDialog(listenerSubject)
    }
}