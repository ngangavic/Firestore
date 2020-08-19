package com.ngangavictor.firestore.reset

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
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.login.LoginActivity
import com.ngangavictor.firestore.register.RegisterActivity

class ResetActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText

    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var buttonReset: Button

    private lateinit var alert: AlertDialog

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        editTextEmail = findViewById(R.id.editTextEmail)

        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonReset = findViewById(R.id.buttonReset)

        auth = Firebase.auth

        buttonReset.setOnClickListener {
            if (TextUtils.isEmpty(editTextEmail.text.toString())) {
                editTextEmail.requestFocus()
                editTextEmail.error = "Cannot be empty"
            } else {
                loadingAlert()
                auth.sendPasswordResetEmail(editTextEmail.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            alert.cancel()
                            messageAlert("Success", "Password reset link sent your email.")
                        } else {
                            alert.cancel()
                            messageAlert("Error", "Error: " + it.exception?.message)
                        }
                    }
            }
        }

        buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
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
        alert=messageAlert.create()
        alert.show()
    }

}