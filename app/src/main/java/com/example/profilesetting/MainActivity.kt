package com.example.profilesetting

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRgister: Button
    lateinit var textView3 : TextView
    lateinit var db : FirebaseFirestore
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var userId : String

    // Define a variable to store the latitude and longitude values
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    fun isValidEmail(email: String): Boolean {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex())
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etAddress = findViewById(R.id.etAddress)
        etPassword = findViewById(R.id.etPassword)
        btnRgister = findViewById(R.id.btnRegister)
        textView3 = findViewById(R.id.textView3)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()


        textView3.setOnClickListener {
            val intent = Intent(this, Sign_In_Activity::class.java)
            startActivity(intent)
        }

        btnRgister.setOnClickListener {

// getting the text
            val fName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val phone = etPhoneNumber.text.toString().trim()
            val locationAddress = etAddress.text.toString().trim()
            if((fName).isNotEmpty() && (email).isNotEmpty() && (password).isNotEmpty() && (phone).isNotEmpty()){

                if (isValidEmail(email)){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            etFullName.text?.clear()
                            etEmail.text?.clear()
                            etPassword.text?.clear()

                            Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show()
                            val curentUser = firebaseAuth.currentUser
                            userId = curentUser?.uid.toString()
                            val userRef = db.collection("Users").document(userId)
                            getLatLngFromAddress(this, locationAddress) { latitude, longitude ->
                                // Use the latitude and longitude values in your application logic
                                if (latitude != 0.0 && longitude != 0.0) {
                                    // Save the latitude and longitude along with other user data to Firestore
                                    val userMap = hashMapOf(
                                        "name" to fName,
                                        "Email" to email,
                                        "Mobile" to phone,
                                        "Location" to locationAddress,
                                        "latitude" to latitude,
                                        "longitude" to longitude
                                    )

                                    // Save the data to Firestore
                                    userRef.set(userMap).addOnSuccessListener {
                                        Log.d(ContentValues.TAG, "user is: $userId")
                                    }

                                    // Continue with other logic or navigation here
                                    val intent = Intent(this, MainActivityCreateProfile::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // Handle the case where the geocoding process failed to find latitude and longitude
                                    Toast.makeText(this, "Failed to find latitude and longitude", Toast.LENGTH_SHORT).show()
                                }
                            }


                        }else{
                            Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Please provide valid email", Toast.LENGTH_SHORT).show()
                }




            }else{
                Toast.makeText(this, "Please do not keep any field empty", Toast.LENGTH_LONG).show()
            }
        }



    }



    fun getLatLngFromAddress(context: Context, mAddress: String, callback: (latitude: Double, longitude: Double) -> Unit) {
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