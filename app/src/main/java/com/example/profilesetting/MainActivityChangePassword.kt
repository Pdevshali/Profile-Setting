package com.example.profilesetting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivityChangePassword : AppCompatActivity() {
    private lateinit var CurrentPassword : EditText
    private lateinit var NewPassword : EditText
    private lateinit var ConfirmPassword : EditText
    private lateinit var ChangePasswordBtn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        CurrentPassword = findViewById(R.id.etCurrentPassword)
        NewPassword = findViewById(R.id.etNewPassword)
        ConfirmPassword = findViewById(R.id.etConfirmPassword)
        ChangePasswordBtn = findViewById(R.id.btChangePassword)

        ChangePasswordBtn.setOnClickListener {
          val  currPass = CurrentPassword.text.toString().trim()
          val  NewPass  = NewPassword.text.toString().trim()
          val  ConfirmPass = ConfirmPassword.text.toString().trim()


        }
    }
}