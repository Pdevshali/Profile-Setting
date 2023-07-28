package com.example.profilesetting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog

private const val REQUEST_CODE_IMAGE_PICK = 0

class MainActivityCreateProfile : AppCompatActivity() {

    private lateinit var ProfileImage : ImageView
    lateinit var dialog: BottomSheetDialog

   @SuppressLint("UseSwitchCompatOrMaterialCode")
   private lateinit var SwitchDark1: Switch

    @SuppressLint("UseCompatLoadingForDrawables")
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
        SwitchDark1 = findViewById(R.id.switch1)



        ProfileImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK)
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
            val intent = Intent(this, MainActivity_Notification::class.java)
            startActivity(intent)
        }

        securityButton.setOnClickListener {
            showToast("Security button clicked")
            showToast("Edit button clicked")
            val intent = Intent(this, MainActivitySecurity::class.java)
            startActivity(intent)
            finish()
        }


        SwitchDark1.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        SwitchDark1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Dark mode is enabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(this, "Dark mode turned on", Toast.LENGTH_SHORT).show()
            } else {
                // Dark mode is disabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(this, "Dark mode turned off", Toast.LENGTH_SHORT).show()
            }
            // Recreate the activity to apply the new night mode setting immediately
            recreate()
        }


        friendsButton.setOnClickListener {
            showToast("Invite Friends button clicked")
//            val intent = Intent(this, MainActivityInviteFriends::class.java)
//            startActivity(intent)
        }

// Initializing dialog Alert box
        dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.custom_dialogue)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.bg_alert))


        // Creating variables for custom dialog.xml layout
        var buttonCancel = dialog.findViewById<Button>(R.id.btnCancel)
        var buttonLogout = dialog.findViewById<Button>(R.id.btnLogout)


        buttonCancel?.setOnClickListener {
            dialog.dismiss()
        }

        buttonLogout?.setOnClickListener {
            Toast.makeText(this, "We successfully receive your feedback", Toast.LENGTH_SHORT).show()

            // logic of logOut feature

        }


        logoutButton.setOnClickListener {
            showToast("Logout button clicked")
            dialog.show()
        }
    }





    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    // to set the profile of user with selected image from gallery
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
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