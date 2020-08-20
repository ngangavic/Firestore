package com.ngangavictor.firestore.school.ui.classes

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R

class ClassFragment : Fragment() {

    private lateinit var classViewModel: ClassViewModel

    private lateinit var root: View

    private lateinit var alert: AlertDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    private lateinit var recyclerViewClasses: RecyclerView
    private lateinit var fabAddClass: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classViewModel =
            ViewModelProviders.of(this).get(ClassViewModel::class.java)

        root = inflater.inflate(R.layout.fragment_class, container, false)

        auth = Firebase.auth
        database = Firebase.firestore

        recyclerViewClasses = root.findViewById(R.id.recyclerViewClasses)
        fabAddClass = root.findViewById(R.id.fabAddClass)

        fabAddClass.setOnClickListener {
            addClassAlert()
        }

        return root
    }


    private fun addClassAlert() {
        val editTextClassName = EditText(requireContext())
        editTextClassName.hint = "Enter class name"

        val classAlert = AlertDialog.Builder(requireContext())
        classAlert.setCancelable(false)
            .setTitle("Add Class")
            .setView(editTextClassName)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Ok") { _, _ ->
                if (TextUtils.isEmpty(editTextClassName.text.toString())) {
                    editTextClassName.requestFocus()
                    editTextClassName.error = "Cannot be empty"
                } else {
                    addClass(editTextClassName.text.toString())
                }
            }

        alert = classAlert.create()
        alert.show()
    }

    private fun addClass(className: String) {

        val data = hashMapOf(
            className.replace(" ","") to className
        )

        database.collection("classes").document(auth.currentUser!!.uid)
            .set(data,SetOptions.merge()).addOnCompleteListener {
                alert.cancel()
                if (it.isSuccessful) {
                    Snackbar.make(requireView(), "Class added", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        "Error while adding class. Try again",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }

}