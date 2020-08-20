package com.ngangavictor.firestore

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngangavictor.firestore.login.LoginActivity
import com.ngangavictor.firestore.register.DetailsActivity
import com.ngangavictor.firestore.register.RegisterActivity
import com.ngangavictor.firestore.school.SchoolActivity
import com.ngangavictor.firestore.utils.LocalSharedPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button

    private lateinit var auth: FirebaseAuth

    private lateinit var localSharedPreferences: LocalSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRegister = findViewById(R.id.buttonRegister)
        buttonLogin = findViewById(R.id.buttonLogin)

        auth = Firebase.auth

        localSharedPreferences = LocalSharedPreferences(this@MainActivity)

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            if (auth.currentUser!!.isEmailVerified) {
                if (localSharedPreferences.getSchoolDetailsPref("school_details") == "yes") {
                    startActivity(Intent(this@MainActivity, SchoolActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@MainActivity, DetailsActivity::class.java))
                    finish()
                }
            } else {
                auth.currentUser!!.sendEmailVerification()
                auth.signOut()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Please verify your email. Check your email inbox",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}