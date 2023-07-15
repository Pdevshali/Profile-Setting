package com.example.profilesetting

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import java.net.URI

class MainActivityCreateProfile : AppCompatActivity() {

    private lateinit var selectedImg : URI
    private lateinit var ProfileImage : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createprofile)

        val profileButton = findViewById<TextView>(R.id.Profile)
        ProfileImage = findViewById(R.id.profilepic)
        val editButton = findViewById<ImageButton>(R.id.edit)
        val vertButton = findViewById<ImageButton>(R.id.vert)
        val editProfileButton = findViewById<ImageButton>(R.id.imageButton1)
        val notificationButton = findViewById<ImageButton>(R.id.imageButton2)
        val securityButton = findViewById<ImageButton>(R.id.imageButton3)
        val darkModeButton = findViewById<ImageButton>(R.id.imageButton4)
        val friendsButton = findViewById<ImageButton>(R.id.imagebutton5)
        val logoutButton = findViewById<ImageButton>(R.id.imageButton6)

        ProfileImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        profileButton.setOnClickListener {
            showToast("Profile button clicked")
        }

        editButton.setOnClickListener {
            showToast("Edit button clicked")

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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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


    // to set the profile of user with selected image from gallery
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            imageUri?.let {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
                ProfileImage.setImageBitmap(bitmap)
            }
        }
    }
}