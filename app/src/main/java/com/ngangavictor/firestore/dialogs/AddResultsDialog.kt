package com.ngangavictor.firestore.dialogs

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.listeners.ListenerResult
import com.ngangavictor.firestore.utils.LocalSharedPreferences

class AddResultsDialog(private val listenerResult: ListenerResult) : DialogFragment() {

    lateinit var root: View

    lateinit var spinnerExam: Spinner
    lateinit var spinnerSubject: Spinner

    lateinit var buttonCancel: Button
    lateinit var buttonAdd: Button

    lateinit var database: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    lateinit var spinnerExamAdapter: ArrayAdapter<String>
    lateinit var spinnerSubjectAdapter: ArrayAdapter<String>

    private lateinit var examList: List<String>
    private lateinit var classList: List<String>
    private lateinit var subjectList: List<String>
    private lateinit var examKeyList: List<String>

    private var pst: Int = 0

    private lateinit var alert: AlertDialog

    private lateinit var localSharedPreferences: LocalSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = layoutInflater.inflate(R.layout.dialog_add_result, container, false)

        spinnerSubject = root.findViewById(R.id.spinnerSubject)
        spinnerExam = root.findViewById(R.id.spinnerExam)

        buttonCancel = root.findViewById(R.id.buttonCancel)
        buttonAdd = root.findViewById(R.id.buttonAdd)

        database = Firebase.firestore
        auth = Firebase.auth

        classList = ArrayList()
        examList = ArrayList()
        subjectList = ArrayList()
        examKeyList = ArrayList()

        (classList as ArrayList<String>).add("Select Class")
        (examList as ArrayList<String>).add("Select Exam")
        (subjectList as ArrayList<String>).add("Select Subject")
        (examKeyList as ArrayList<String>).add("Select Key")

        spinnerExamAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, examList)
        spinnerExamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerExam.adapter = spinnerExamAdapter


        spinnerSubjectAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subjectList)
        spinnerSubjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSubject.adapter = spinnerSubjectAdapter


        database.collection("schools").document(auth.currentUser!!.uid).collection("subjects")
            .addSnapshotListener { value, error ->
                for (i in value!!) {
                    (subjectList as ArrayList<String>).add(i.data.get("subjectName").toString())
                }

                spinnerSubjectAdapter.notifyDataSetChanged()
            }

        database.collection("schools").document(auth.currentUser!!.uid).collection("exams")
            .addSnapshotListener { value, error ->
                for (i in value!!) {
                    (examKeyList as ArrayList<String>).add(i.id)

                    (classList as ArrayList<String>).add(i.data.get("class").toString())

                    (examList as ArrayList<String>).add(
                        i.data.get("class").toString() + " " + i.data.get(
                            "examName"
                        ).toString() + " " + i.data.get("examTerm")
                            .toString() + " " + i.data.get("examYear")
                            .toString()
                    )
                }

                spinnerExamAdapter.notifyDataSetChanged()
            }

        spinnerExam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                pst = position
                Log.e("SELECTED EXAM", parent!!.getItemAtPosition(position).toString())
                Log.e("SELECTED CLASS", classList[position])
                Log.e("SELECTED EXAM KEY", examKeyList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        buttonAdd.setOnClickListener {
            addExam()
        }

        buttonCancel.setOnClickListener {
            dialog!!.dismiss()
        }

        localSharedPreferences = LocalSharedPreferences(requireContext())

        return root
    }

    private fun addExam() {

        if (spinnerExam.selectedItem.toString() == "Select Exam") {
            spinnerExam.performClick()
        } else if (spinnerSubject.selectedItem.toString() == "Select Subject") {
            spinnerSubject.performClick()
        } else {
            loadingAlert()

            localSharedPreferences.saveSelectedExamPref("examKey", examKeyList[pst])
            localSharedPreferences.saveSelectedExamPref(
                "examSubject",
                spinnerSubject.selectedItem.toString()
            )

            listenerResult.selectedExam(examKeyList[pst], spinnerSubject.selectedItem.toString())

            database.collection("schools").document(auth.currentUser!!.uid)
                .collection("students")
                .document("classes").collection(classList[pst])
                .addSnapshotListener { value, error ->

                    var count = 0

                    for (i in value!!) {

                        if (count != i.data.size) {

                            val default = hashMapOf(
                                "created" to "yes"
                            )
                            database.collection("schools").document(auth.currentUser!!.uid)
                                .collection("results")
                                .document(examKeyList[pst])
                                .collection(spinnerSubject.selectedItem.toString())
                                .document(i.id)
                                .set(default, SetOptions.merge())
                        } else {
                            dialog!!.dismiss()
                            alert.cancel()
                        }

                        count++

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

    fun newInstance(): AddResultsDialog {
        return AddResultsDialog(listenerResult)
    }

}