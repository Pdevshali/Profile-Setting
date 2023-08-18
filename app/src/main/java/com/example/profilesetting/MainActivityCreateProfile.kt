package com.example.profilesetting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class MainActivityCreateProfile : AppCompatActivity() {

    private lateinit var ProfileImage : CircleImageView
    lateinit var dialog: BottomSheetDialog
    lateinit var uploadBtn: Button
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var progressBar: ProgressBar



    @SuppressLint("UseSwitchCompatOrMaterialCode")
   private lateinit var SwitchDark1: Switch

    @SuppressLint("UseCompatLoadingForDrawables", "SuspiciousIndentation")
    private val TAG = "FirebaseStorageManager"
    private val storageRef = FirebaseStorage.getInstance().reference
    private lateinit var db : FirebaseFirestore
    var currFile : Uri? = null


    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle image URI here
        ProfileImage = findViewById(R.id.profilepic)
        ProfileImage.setImageURI(uri)
        currFile = uri
    }
    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
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
        uploadBtn = findViewById(R.id.btnUpload)
        progressBar = findViewById(R.id.progress_upload)
        SwitchDark1 = findViewById(R.id.switch1)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        // Set the data
        setData()

// uploading Image to firebase
        ProfileImage.setOnClickListener {
            getImageFromGallery.launch("image/*")
        }

        uploadBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            uploadImage("User")
            progressBar.visibility = View.GONE

        }




        profileButton.setOnClickListener {
            showToast("Profile button clicked")
        }

        editButton.setOnClickListener {
          val intent = Intent(this, ShowMatch_Activity::class.java)
            startActivity(intent)

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

        }


//        SwitchDark1.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        SwitchDark1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Dark mode is enabled
                darkModeButton.setColorFilter(ContextCompat.getColor(this, R.color.white))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

//                Toast.makeText(this, "Dark mode turned on", Toast.LENGTH_SHORT).show()

            } else {
                // Dark mode is disabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                Toast.makeText(this, "Dark mode turned off", Toast.LENGTH_SHORT).show()

            }
            // Recreate the activity to apply the new night mode setting immediately
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
        val buttonCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val buttonLogout = dialog.findViewById<Button>(R.id.btnLogout)


        buttonCancel?.setOnClickListener {
            dialog.dismiss()
        }

        buttonLogout?.setOnClickListener {

            // logic of logOut feature
            val currentUser = firebaseAuth.currentUser

            if (currentUser == null) {
                // If the current user is already null (not logged in), just return
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()

            }

            // Log out the current user
            Toast.makeText(this, "User  logged out", Toast.LENGTH_SHORT).show()
            firebaseAuth.signOut()

            // After logging out, redirect to the Login activity
            val intent = Intent(this, Sign_In_Activity::class.java)
            startActivity(intent)
            finish()


        }


        logoutButton.setOnClickListener {
            showToast("Logout button clicked")
            dialog.show()
        }
    }



    private fun uploadImage(folderName: String) {
        if(currFile == null){
            return
        }
        // For Image Upload
        val userId = firebaseAuth.currentUser?.uid ?: return
        val imageName = "${System.currentTimeMillis()}.jpg"

        val imageRef = storageRef.child("$folderName/$imageName")
        val uploadTask = imageRef.putFile(currFile!!)

        uploadTask.addOnSuccessListener {
            Toast.makeText(this, "Image Successfully uploaded", Toast.LENGTH_LONG).show()

            it.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener {
                    val userRef = db.collection("Users").document(userId)
                    val updateMap = mapOf(
                        "imageUrl" to it.toString()
                    )
                    userRef.update(updateMap).addOnSuccessListener {
                        Log.d(TAG, "Image URL updated in Firestore")

                    }

                }
        }

    }

    private fun setData() {
        val userId = firebaseAuth.currentUser!!.uid
        val ref = db.collection("Users").document(userId)

        ref.get().addOnSuccessListener {
            if(it!=null) {
                val imageUrl = it.data?.get("imageUrl")?.toString()
                if (imageUrl != null && imageUrl.isNotEmpty()){
                    Picasso.get()
                        .load(imageUrl)
                        .into(ProfileImage)
                } else {
                    // Load and display a default profile image using Picasso
                    ProfileImage.setImageResource(R.drawable.img)
                }


            }
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(ContentValues.TAG, it1) }
        }
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}