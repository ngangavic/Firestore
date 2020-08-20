package com.ngangavictor.firestore.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.login.LoginActivity
import com.ngangavictor.firestore.reset.ResetActivity
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var buttonReset: Button

    private lateinit var alert: AlertDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)

        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonReset = findViewById(R.id.buttonReset)

        auth = Firebase.auth
        database = Firebase.firestore

        buttonRegister.setOnClickListener {
            register()
        }

        buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        buttonReset.setOnClickListener {
            startActivity(Intent(this, ResetActivity::class.java))
            finish()
        }

    }

    private fun register() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        when {
            TextUtils.isEmpty(email) -> {
                editTextEmail.requestFocus()
                editTextEmail.error = "Cannot be empty"
            }

            TextUtils.isEmpty(password) -> {
                editTextPassword.requestFocus()
                editTextPassword.error = "Cannot be empty"
            }

            !verifyEmail(email) -> {
                editTextEmail.requestFocus()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Email not valid",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            checkPassword(password) -> {
                editTextPassword.requestFocus()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Password too short",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            else -> {
                loadingAlert()

                val sdf = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()

                            val school = hashMapOf(
                                "uid" to auth.currentUser?.uid.toString(),
                                "email" to email,
                                "dateOfReg" to currentDate
                            )

                            database.collection("schools").document(auth.currentUser!!.uid)
                                .set(school)
                                .addOnCompleteListener { doc ->
                                    if (doc.isSuccessful) {
                                        alert.cancel()
                                        messageAlert(
                                            "Success",
                                            "You were registered successfully. Check your email inbox for email verification."
                                        )
                                        clearText()
                                    } else {
                                        alert.cancel()
                                        messageAlert("Error", "Error: " + doc.exception?.message)
                                        clearText()
                                    }
                                }
                        } else {
                            alert.cancel()
                            messageAlert("Error", "Error: " + it.exception?.message)
                            clearText()
                        }
                    }

            }
        }
    }

    private fun verifyEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun checkPassword(pass: String): Boolean {
        return pass.length < 6
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
            if (title == "Success") {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                dialog.cancel()
            }
        }
        alert = messageAlert.create()
        alert.show()
    }

    private fun clearText() {
        editTextEmail.text.clear()
        editTextPassword.text.clear()
    }

}