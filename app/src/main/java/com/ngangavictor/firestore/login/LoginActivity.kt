package com.ngangavictor.firestore.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.R
import com.ngangavictor.firestore.register.DetailsActivity
import com.ngangavictor.firestore.register.RegisterActivity
import com.ngangavictor.firestore.reset.ResetActivity
import com.ngangavictor.firestore.school.SchoolActivity
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail:EditText
    private lateinit var editTextPassword:EditText

    private lateinit var buttonLogin:Button
    private lateinit var buttonRegister:Button
    private lateinit var buttonReset:Button

    private lateinit var alert:AlertDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail=findViewById(R.id.editTextEmail)
        editTextPassword=findViewById(R.id.editTextPassword)

        buttonLogin=findViewById(R.id.buttonLogin)
        buttonRegister=findViewById(R.id.buttonRegister)
        buttonReset=findViewById(R.id.buttonReset)

        auth = Firebase.auth
        database = Firebase.firestore

        buttonLogin.setOnClickListener {
            login()
        }

        buttonRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        buttonReset.setOnClickListener {
            startActivity(Intent(this,ResetActivity::class.java))
            finish()
        }

    }

    private fun checkDocument(){
        database.collection("schools").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                if (it.data!!.size==3){
                    alert.cancel()
                    clearText()
                    startActivity(Intent(this@LoginActivity,DetailsActivity::class.java))
                    finish()
                }else{
                    alert.cancel()
                    clearText()
                    startActivity(Intent(this@LoginActivity,SchoolActivity::class.java))
                    finish()
                }
            }
    }

    private fun login() {
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
            else -> {
                loadingAlert()

                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            if(auth.currentUser!!.isEmailVerified){
                                //check data
                                checkDocument()
                            }else{
                                auth.currentUser?.sendEmailVerification()
                                alert.cancel()
                                messageAlert("Alert","Please verify your email address.")
                                clearText()
                            }
                        }else{
                            alert.cancel()
                            messageAlert("Error","Error: "+it.exception?.message)
                            clearText()
                        }
                    }

            }
        }
    }

    private fun loadingAlert(){
        val loadingAlert= AlertDialog.Builder(this)
        val progressBar= ProgressBar(this)
        loadingAlert.setCancelable(false)
        loadingAlert.setView(progressBar)
        alert=loadingAlert.create()
        alert.show()
    }

    private fun messageAlert(title:String,message:String){
        val messageAlert= AlertDialog.Builder(this)
        messageAlert.setCancelable(false)
        messageAlert.setTitle(title)
        messageAlert.setMessage(message)
        messageAlert.setPositiveButton("OK") { dialog, _ ->
            dialog.cancel()
        }
        alert=messageAlert.create()
        alert.show()
    }

    private fun clearText(){
        editTextEmail.text.clear()
        editTextPassword.text.clear()
    }


}