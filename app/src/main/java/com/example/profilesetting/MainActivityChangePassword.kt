package com.example.profilesetting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivityChangePassword : AppCompatActivity() {
    private lateinit var CurrentPassword: EditText
    private lateinit var NewPassword: EditText
    private lateinit var ConfirmPassword: EditText
    private lateinit var ChangePasswordBtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        CurrentPassword = findViewById(R.id.etCurrentPassword)
        NewPassword = findViewById(R.id.etNewPassword)
        ConfirmPassword = findViewById(R.id.etConfirmPassword)
        ChangePasswordBtn = findViewById(R.id.btnChangePassword)

        ChangePasswordBtn.setOnClickListener {
            val currPass = CurrentPassword.text.toString().trim()
            val NewPass = NewPassword.text.toString().trim()
            val ConfirmPass = ConfirmPassword.text.toString().trim()

            if(currPass.isNotEmpty() && NewPass.isNotEmpty() && ConfirmPass.isNotEmpty()){
                Toast.makeText(this, "Fields are not empty", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "The Password is Changed", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Please do not empty any field", Toast.LENGTH_SHORT).show()
            }

        }
    }
}