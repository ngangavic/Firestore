package com.ngangavictor.firestore.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.school.SchoolActivity
import com.ngangavictor.firestore.utils.LocalSharedPreferences

class DetailsActivity : AppCompatActivity() {

    private lateinit var editTextSchoolName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextPopulation: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextLocation: EditText

    private lateinit var buttonContinue: Button

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var alert: AlertDialog

    private lateinit var localSharedPreferences: LocalSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        editTextSchoolName = findViewById(R.id.editTextSchoolName)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextPopulation = findViewById(R.id.editTextPopulation)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextLocation = findViewById(R.id.editTextLocation)

        buttonContinue = findViewById(R.id.buttonContinue)

        db = Firebase.firestore
        auth = Firebase.auth

        localSharedPreferences= LocalSharedPreferences(this@DetailsActivity)

        buttonContinue.setOnClickListener {
            saveDetails()
        }

    }

    private fun saveDetails() {
        val schoolName = editTextSchoolName.text.toString()
        val phone = editTextPhone.text.toString()
        val population = editTextPopulation.text.toString()
        val address = editTextAddress.text.toString()
        val location = editTextLocation.text.toString()

        when {
            TextUtils.isEmpty(schoolName) -> {
                editTextSchoolName.requestFocus()
                editTextSchoolName.error = "Cannot be empty"
            }

            TextUtils.isEmpty(phone) -> {
                editTextPhone.requestFocus()
                editTextPhone.error = "Cannot be empty"
            }

            TextUtils.isEmpty(population) -> {
                editTextPopulation.requestFocus()
                editTextPopulation.error = "Cannot be empty"
            }

            TextUtils.isEmpty(address) -> {
                editTextAddress.requestFocus()
                editTextAddress.error = "Cannot be empty"
            }

            TextUtils.isEmpty(location) -> {
                editTextLocation.requestFocus()
                editTextLocation.error = "Cannot be empty"
            }

            !checkSchoolName(schoolName) -> {
                editTextSchoolName.requestFocus()
                editTextSchoolName.error = "Invalid name"
            }

            !checkPhone(phone) -> {
                editTextPhone.requestFocus()
                editTextPhone.error = "Invalid phone number"
            }

            !checkLocation(location) -> {
                editTextLocation.requestFocus()
                editTextLocation.error = "Invalid location"
            }

            else -> {
                loadingAlert()
                val school = hashMapOf(
                    "schoolName" to schoolName,
                    "schoolPhone" to phone,
                    "schoolPopulation" to population,
                    "schoolAddress" to address,
                    "schoolLocation" to location
                )
                db.collection("schools").document(auth.currentUser!!.uid)
                    .set(school, SetOptions.merge())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            localSharedPreferences.saveSchoolDetailsPref("school_details","yes")
                            alert.cancel()
                            startActivity(Intent(this@DetailsActivity, SchoolActivity::class.java))
                            finish()
                        } else {
                            localSharedPreferences.saveSchoolDetailsPref("school_details","no")
                            alert.cancel()
                            messageAlert("Error", "Message: " + it.exception!!.message.toString())
                        }
                    }
            }
        }

    }

    private fun checkSchoolName(schoolName: String): Boolean {
        return schoolName.contains(" ")
    }

    private fun checkPhone(phone: String): Boolean {
        return phone.length == 10
    }

    private fun checkLocation(location: String): Boolean {
        return location.contains(",")
    }

    private fun loadingAlert() {
        val loadingAlert = AlertDialog.Builder(this)
        val progressBar = ProgressBar(this)
        loadingAlert.setCancelable(false)
        loadingAlert.setView(progressBar)
        alert = loadingAlert.create()
        alert.show()
    }

    private fun messageAlert(title: String, message: String) {
        val messageAlert = AlertDialog.Builder(this)
        messageAlert.setCancelable(false)
        messageAlert.setTitle(title)
        messageAlert.setMessage(message)
        messageAlert.setPositiveButton("OK") { dialog, _ ->
            dialog.cancel()
        }
        alert = messageAlert.create()
        alert.show()
    }

}