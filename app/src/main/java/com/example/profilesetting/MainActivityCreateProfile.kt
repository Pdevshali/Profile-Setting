package com.example.profilesetting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class MainActivityCreateProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createprofile)

        val profileButton = findViewById<TextView>(R.id.Profile)
        val editButton = findViewById<ImageButton>(R.id.edit)
        val vertButton = findViewById<ImageButton>(R.id.vert)
        val editProfileButton = findViewById<ImageButton>(R.id.imageButton1)
        val notificationButton = findViewById<ImageButton>(R.id.imageButton2)
        val securityButton = findViewById<ImageButton>(R.id.imageButton3)
        val darkModeButton = findViewById<ImageButton>(R.id.imageButton4)
        val friendsButton = findViewById<ImageButton>(R.id.imagebutton5)
        val logoutButton = findViewById<ImageButton>(R.id.imageButton6)

        profileButton.setOnClickListener {
            showToast("Profile button clicked")
        }

        editButton.setOnClickListener {
            showToast("Edit button clicked")
            val intent = Intent(this, MainActivityEdit::class.java)
            startActivity(intent)
            finish()
        }

        vertButton.setOnClickListener {
            showToast("Vert button clicked")
        }

        editProfileButton.setOnClickListener {
            showToast("Edit Profile button clicked")
            val intent = Intent(this, MainActivityEdit::class.java)
            startActivity(intent)
        }

        notificationButton.setOnClickListener {
            showToast("Notification button clicked")
            val intent = Intent(this, MainActivityNotification::class.java)
            startActivity(intent)
        }

        securityButton.setOnClickListener {
            showToast("Security button clicked")
            showToast("Edit button clicked")
            val intent = Intent(this, MainActivitySecurity::class.java)
            startActivity(intent)
            finish()
        }

        darkModeButton.setOnClickListener {
            val isDarkModeEnabled = darkModeButton.isSelected
            darkModeButton.isSelected = !isDarkModeEnabled

            if (isDarkModeEnabled) {
                // Dark mode is enabled
                darkModeButton.setColorFilter(ContextCompat.getColor(this, R.color.Watermelon))
                showToast("Dark Mode turned off")
                // TODO: Apply light mode theme or update UI for light mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                // Dark mode is disabled
                darkModeButton.setColorFilter(ContextCompat.getColor(this, android.R.color.black))
                showToast("Dark Mode turned on")
                // TODO: Apply dark mode theme or update UI for dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }


        friendsButton.setOnClickListener {
            showToast("Invite Friends button clicked")
        }

        logoutButton.setOnClickListener {
            showToast("Logout button clicked")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}