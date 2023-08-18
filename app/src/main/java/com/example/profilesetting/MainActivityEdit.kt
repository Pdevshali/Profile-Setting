package com.example.profilesetting

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
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
    private lateinit var etPhoneNumber: EditText
//    private lateinit var spinner1: Spinner
    private lateinit var etAbout: EditText
    private lateinit var etInterest: EditText
    private lateinit var etAddress: EditText
    private lateinit var etAge: EditText
    private lateinit var btnSaveChanges: Button
    lateinit var ImageV : ImageView
    lateinit var userId : String


    private var db = Firebase.firestore
    lateinit var firebaseAuth : FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile1)

        // Find views by their respective IDs
        etFullName = findViewById(R.id.etFullName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
//        spinner1 = findViewById(R.id.spinner1)
        etAbout = findViewById(R.id.etAbout)
        etAge = findViewById(R.id.etAge)
        etInterest = findViewById(R.id.etInterest)
        etAddress = findViewById(R.id.etAddress)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)
        ImageV = findViewById(R.id.imageView)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        // Set text color to watermelon color for EditText fields
        etFullName.setTextColor(Color.parseColor("#FF4D67"))
        etPhoneNumber.setTextColor(Color.parseColor("#FF4D67"))
        etAbout.setTextColor(Color.parseColor("#FF4D67"))
        etInterest.setTextColor(Color.parseColor("#FF4D67"))
        etAddress.setTextColor(Color.parseColor("#FF4D67"))


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
            val phone = etPhoneNumber.text.toString().trim()
            val age = etAge.text.toString().trim()
            val about = etAbout.text.toString().trim()
            val interest = etInterest.text.toString().trim()
            val locationAddress = etAddress.text.toString().trim()
            val userId = firebaseAuth.currentUser!!.uid

            val curentUser = firebaseAuth.currentUser

            getLatLngFromAddress(this, locationAddress) { latitude, longitude ->
                // Use the latitude and longitude values in your application logic
                if (latitude != 0.0 && longitude != 0.0) {
                    // Save the latitude and longitude along with other user data to Firestore
                    val updateMap = mapOf(
                        "name" to fName,
                        "Mobile" to phone,
                        "age" to age,
                        "about" to about,
                        "interest" to interest,
                        "Location" to locationAddress,
                        "latitude" to latitude,
                        "longitude" to longitude
                    )

                    db.collection("Users").document(userId).update(updateMap).addOnSuccessListener {
                        Toast.makeText(this, "Data successfully updated...", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed...", Toast.LENGTH_SHORT).show()
                    }


                } else {
                    // Handle the case where the geocoding process failed to find latitude and longitude
                    Toast.makeText(this, "Failed to find latitude and longitude", Toast.LENGTH_SHORT).show()
                }
            }


        }


    }


    private fun setData(){
        val userId = firebaseAuth.currentUser!!.uid
        val ref = db.collection("Users").document(userId)


        ref.get().addOnSuccessListener {
            if (it!=null) {
                val Name = it.data?.get("name")?.toString()
                val Phone = it.data?.get("Mobile")?.toString()

                etFullName.text = Editable.Factory.getInstance().newEditable(Name)
                etPhoneNumber.text = Editable.Factory.getInstance().newEditable(Phone)

            }
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(ContentValues.TAG, it1) }
        }
    }



    private  fun getLatLngFromAddress(context: Context, mAddress: String, callback: (latitude: Double, longitude: Double) -> Unit) {
        val coder = Geocoder(context)
        lateinit var address: List<Address>
        try {
            address = coder.getFromLocationName(mAddress, 5)!!
            if (address.isEmpty()) {
                callback(0.0, 0.0) // Call the callback with default values if no address is found
                return
            }
            val location = address[0]
            val latitude = location.latitude
            val longitude = location.longitude
            callback(latitude, longitude) // Call the callback with the latitude and longitude values
        } catch (e: Exception) {
            callback(0.0, 0.0) // Call the callback with default values in case of an error
        }
        return
    }

    }




