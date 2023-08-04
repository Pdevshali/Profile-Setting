package com.example.profilesetting

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityEdit : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
//    private lateinit var spinner1: Spinner
    private lateinit var etAbout: EditText
    private lateinit var etInterest: EditText
//    private lateinit var etAddress: EditText
    private lateinit var etAge: EditText
    private lateinit var btnSaveChanges: Button
    lateinit var ImageV : ImageView

   private var db = Firebase.firestore
    lateinit var firebaseAuth : FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile1)

        // Find views by their respective IDs
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
//        spinner1 = findViewById(R.id.spinner1)
        etAbout = findViewById(R.id.etAbout)
        etAge = findViewById(R.id.etAge)
        etInterest = findViewById(R.id.etInterest)
//        etAddress = findViewById(R.id.etAddress)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)
        ImageV = findViewById(R.id.imageView)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        // Set text color to watermelon color for EditText fields
        etFullName.setTextColor(Color.parseColor("#FF4D67"))
        etEmail.setTextColor(Color.parseColor("#FF4D67"))
        etPhoneNumber.setTextColor(Color.parseColor("#FF4D67"))
//        etAbout.setTextColor(Color.parseColor("#FF4D67"))
//        etInterest.setTextColor(Color.parseColor("#FF4D67"))
//        etAddress.setTextColor(Color.parseColor("#FF4D67"))


        // back to the previous Activity
        ImageV.setOnClickListener {
            val intent = Intent(this, MainActivityCreateProfile::class.java)
            startActivity(intent)
            finish()
        }


//
//        val genderOptions = arrayOf("Male", "Female", "Other")
//
//// Create an ArrayAdapter using the string array and a default spinner layout
//        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
//// Specify the layout to use when the list of choices appears
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//// Apply the adapter to the spinner
//        spinner1.adapter = spinnerAdapter
//
//        // Set item selection listener for spinner1 (Gender)
//        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                val selectedGender = parent.getItemAtPosition(position).toString()
//                // Handle the selected gender value
//                when (selectedGender) {
//                    "Male" -> {
//                        // Perform actions for Male selection
//                        Toast.makeText(
//                            this@MainActivityEdit,
//                            "male is selected",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    "Female" -> {
//                        // Perform actions for Female selection
//                        Toast.makeText(
//                            this@MainActivityEdit,
//                            "Female is selected",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    "Other" -> {
//                        // Perform actions for Other selection
//                        Toast.makeText(
//                            this@MainActivityEdit,
//                            "Other  is selected",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Handle the case when nothing is selected
//                Toast.makeText(this@MainActivityEdit, "Please select the field", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }

        setData()


        // Set click listener for the btnSaveChanges button
        btnSaveChanges.setOnClickListener {
            val fName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhoneNumber.text.toString().trim()
            val age = etAge.text.toString().trim()
            val about = etAbout.text.toString().trim()
            val interest = etInterest.text.toString().trim()
            val userId = firebaseAuth.currentUser!!.uid




            // update

            val updateMap = mapOf(
                "name" to fName,
                "Email" to email,
                "Mobile" to phone,
                "age" to age,
                "about" to about,
                "interest" to interest
            )


            db.collection("Users").document(userId).update(updateMap).addOnSuccessListener {
                Toast.makeText(this, "Data successfully updated...", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed...", Toast.LENGTH_SHORT).show()
            }

        }


    }


    private fun setData(){
        val userId = firebaseAuth.currentUser!!.uid
        val ref = db.collection("Users").document(userId)


        ref.get().addOnSuccessListener {
            if (it!=null) {
                val Name = it.data?.get("name")?.toString()
                val Email = it.data?.get("Email")?.toString()
                val Phone = it.data?.get("Mobile")?.toString()

                etFullName.text = Editable.Factory.getInstance().newEditable(Name)
                etEmail.text = Editable.Factory.getInstance().newEditable(Email)
                etPhoneNumber.text = Editable.Factory.getInstance().newEditable(Phone)

            }
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(ContentValues.TAG, it1) }
        }
    }

    }




