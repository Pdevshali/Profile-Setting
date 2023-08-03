package com.example.profilesetting

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Sign_In_Activity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    lateinit var textView4 : TextView
    lateinit var btnSignIn : Button
    lateinit var firebaseAuth : FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        textView4 = findViewById(R.id.textView4)
        btnSignIn = findViewById(R.id.btnSignIn)
        firebaseAuth = FirebaseAuth.getInstance()


        // Don't have any account
        textView4.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            // getting the information
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if ((email).isNotEmpty() && (password).isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
//                        to navigate between fragments within the same activity,
                        etEmail.text.clear()
                        etPassword.text.clear()
                        goToCreateProfileActivity()


                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "Please do not keep any field empty", Toast.LENGTH_LONG).show()
            }


        }

    }

    private fun goToCreateProfileActivity() {
        val intent = Intent(this, MainActivityCreateProfile::class.java)
        startActivity(intent)
        Toast.makeText(this, "You are logged In", Toast.LENGTH_LONG).show()
    }
}
