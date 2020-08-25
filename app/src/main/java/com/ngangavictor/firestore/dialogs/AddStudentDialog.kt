package com.ngangavictor.firestore.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.listeners.ListenerStudent
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class AddStudentDialog(val listenerStudent: ListenerStudent) : DialogFragment() {

    lateinit var root: View

    private lateinit var textViewData: TextView
    private lateinit var textViewMessage: TextView

    private lateinit var buttonUpload: Button
    private lateinit var buttonSelectFile: Button

    lateinit var alert: AlertDialog

    lateinit var database: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    private lateinit var input: InputStream

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.dialog_add_student, container, false)

        textViewData = root.findViewById(R.id.textViewData)
        textViewMessage = root.findViewById(R.id.textViewMessage)

        buttonUpload = root.findViewById(R.id.buttonUpload)
        buttonSelectFile = root.findViewById(R.id.buttonSelectFile)

        database = Firebase.firestore
        auth = Firebase.auth

        buttonUpload.visibility = View.GONE

        buttonSelectFile.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.setType("*/*")
            startActivityForResult(Intent.createChooser(i, "Select File"), 200)
        }

        buttonUpload.setOnClickListener {
            uploadData()
        }

        textViewData.movementMethod = ScrollingMovementMethod()

        return root
    }

    private fun uploadData() {
        loadingAlert()
        val resultList: MutableList<Any> = ArrayList()
        val reader = BufferedReader(InputStreamReader(input))
        var count = 0
        try {
            textViewData.append("The following data was uploaded" + "\n")

            val iterator = reader.lineSequence().iterator()
            while (iterator.hasNext()) {
                val line = iterator.next()
                Log.e("LINE", line.split(",").toString())
                resultList.add(line.split(","))

            }
            reader.close()

            val itr = resultList.iterator()
            if (itr.hasNext()) {

                itr.forEach {
                    val data = it.toString().replace("[", "").replace("]", "")

                    val adm = "ADM: " + data.substringBefore(",") + " "
                    val name = "NAME: " + data.substringAfter(",").substringBefore(",") + " "
                    val classs = "CLASS: " + data.substringAfter(",").substringAfter(",") + " "

                    Log.e("ADM:", it.toString().substringBefore(","))
                    Log.e("NAME:", it.toString().substringAfter(",").substringBefore(","))
                    Log.e("CLASS", it.toString().substringAfter(",").substringAfter(","))

                    textViewData.append(adm + name + classs + "\n")

                    val insert = hashMapOf(
                        "NAME" to data.substringAfter(",").substringBefore(","),
                        "CLASS" to data.substringAfter(",").substringAfter(",")
                    )

                    database.collection("schools").document(auth.currentUser!!.uid)
                        .collection("students")
                        .document(data.substringBefore(","))
                        .set(insert)
                        .addOnCompleteListener {itl->
                            if (itl.isSuccessful){
                                alert.cancel()
                                listenerStudent.addStudent("complete")
                            }else{
                                alert.cancel()
                                listenerStudent.addStudent(itl.exception!!.message.toString())
                            }
                        }
                }
            }

        } catch (ex: IOException) {
            throw RuntimeException("Error in reading CSV file: $ex")
        } finally {
            try {
                input.close()
            } catch (e: IOException) {
                throw RuntimeException("Error while closing input stream: $e")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 200 && resultCode == AppCompatActivity.RESULT_OK) {
            buttonUpload.visibility = View.VISIBLE
            textViewMessage.text = "File selected"

            val uri = data?.data
            input = uri?.let { requireActivity().contentResolver.openInputStream(it) }!!
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadingAlert() {
        val progressBar = ProgressBar(requireContext())

        val loadAlert = AlertDialog.Builder(requireContext())
        loadAlert.setCancelable(false)
        loadAlert.setView(progressBar)
        alert = loadAlert.create()
        alert.show()
    }

    fun newInstance(): AddStudentDialog {
        return AddStudentDialog(listenerStudent)
    }
}