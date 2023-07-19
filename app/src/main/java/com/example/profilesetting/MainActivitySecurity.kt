package com.example.profilesetting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class MainActivitySecurity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var SwitchFaceIdStatus: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var SwitchRememberMeStatus: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var SwitchTouchIdStatus: Switch
    private lateinit var BtnChangePassword: Button
    private lateinit var ImageViewIcon: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_security)

        SwitchFaceIdStatus = findViewById(R.id.switchFaceIdStatus)
        SwitchRememberMeStatus = findViewById(R.id.switchRememberMeStatus)
        SwitchTouchIdStatus = findViewById(R.id.switchTouchIdStatus)
        BtnChangePassword = findViewById(R.id.btnChangePassword)
        ImageViewIcon = findViewById(R.id.imageViewIcon)

        ImageViewIcon.setOnClickListener {
            val intent = Intent(this, MainActivityCreateProfile::class.java)
            startActivity(intent)
        }

        SwitchFaceIdStatus.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Perform actions when Face ID switch is turned on
                BtnChangePassword.setBackgroundResource(R.drawable.watermelon_bg)
                enableFaceIdAuthentication()
            } else {
                // Perform actions when Face ID switch is turned off
                BtnChangePassword.setBackgroundResource(android.R.color.transparent)
                disableFaceIdAuthentication()
            }
        }

        SwitchRememberMeStatus.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Perform actions when Remember Me switch is turned on
                rememberLoginCredentials()
            } else {
                // Perform actions when Remember Me switch is turned off
                clearLoginCredentials()
            }
        }

        SwitchTouchIdStatus.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Perform actions when Touch ID switch is turned on
                enableTouchIdAuthentication()
            } else {
                // Perform actions when Touch ID switch is turned off
                disableTouchIdAuthentication()
            }
        }

        BtnChangePassword.setOnClickListener {
            // Perform actions when Change Password button is clicked
            openChangePasswordScreen()
        }
    }

    private fun enableFaceIdAuthentication() {
        // Enable Face ID authentication logic here
        // Example: Update user preferences to enable Face ID authentication
        val preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("FaceIdEnabled", true)
        editor.apply()
    }

    private fun disableFaceIdAuthentication() {
        // Disable Face ID authentication logic here
        // Example: Update user preferences to disable Face ID authentication
        val preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("FaceIdEnabled", false)
        editor.apply()
    }

    private fun rememberLoginCredentials() {
        // Remember login credentials logic here
        // Example: Store the login credentials in shared preferences
        val preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("Username", "exampleuser")
        editor.putString("Password", "examplepassword")
        editor.apply()
    }

    private fun clearLoginCredentials() {
        // Clear saved login credentials logic here
        // Example: Remove the stored login credentials from shared preferences
        val preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove("Username")
        editor.remove("Password")
        editor.apply()
    }

    private fun enableTouchIdAuthentication() {
        // Enable Touch ID authentication logic here
        // Example: Update user preferences to enable Touch ID authentication
        val preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("TouchIdEnabled", true)
        editor.apply()
    }

    private fun disableTouchIdAuthentication() {
        // Disable Touch ID authentication logic here
        // Example: Update user preferences to disable Touch ID authentication
        val preferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("TouchIdEnabled", false)
        editor.apply()
    }

    private fun openChangePasswordScreen() {
        // Open change password screen logic here
        // Example: Start the ChangePasswordActivity
        val intent = Intent(this, MainActivityChangePassword::class.java)
        startActivity(intent)
    }

    companion object
}


