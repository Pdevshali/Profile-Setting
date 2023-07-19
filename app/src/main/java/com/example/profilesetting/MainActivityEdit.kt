package com.example.profilesetting

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivityEdit : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var etAbout: EditText
    private lateinit var etInterest: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnSaveChanges: Button
    lateinit var ImageV : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile1)

        // Find views by their respective IDs
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        etAbout = findViewById(R.id.etAbout)
        etInterest = findViewById(R.id.etInterest)
        etAddress = findViewById(R.id.etAddress)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)
        ImageV  = findViewById(R.id.imageView)

        // Set text color to watermelon color for EditText fields
        etFullName.setTextColor(Color.parseColor("#FF4D67"))
        etEmail.setTextColor(Color.parseColor("#FF4D67"))
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

        // Set click listener for the btnSaveChanges button
        btnSaveChanges.setOnClickListener {
            // Perform actions on button click
            saveChanges()
        }

        val genderOptions = arrayOf("Male", "Female", "Other")

// Create an ArrayAdapter using the string array and a default spinner layout
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
// Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Apply the adapter to the spinner
        spinner1.adapter = spinnerAdapter

        // Set item selection listener for spinner1 (Gender)
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedGender = parent.getItemAtPosition(position).toString()
                // Handle the selected gender value
                when (selectedGender) {
                    "Male" -> {
                        // Perform actions for Male selection
                      Toast.makeText(this@MainActivityEdit, "male is selected", Toast.LENGTH_SHORT).show()
                    }
                    "Female" -> {
                        // Perform actions for Female selection
                        Toast.makeText(this@MainActivityEdit, "Female is selected", Toast.LENGTH_SHORT).show()
                    }
                    "Other" -> {
                        // Perform actions for Other selection
                        Toast.makeText(this@MainActivityEdit, "Other  is selected", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case when nothing is selected
                Toast.makeText(this@MainActivityEdit, "Please select the field", Toast.LENGTH_SHORT).show()
            }
        }


        val ageOptions = arrayOf("18-24", "25-34", "35-44", "45+")

// Create an ArrayAdapter using the string array and a default spinner layout
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageOptions)
// Specify the layout to use when the list of choices appears
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
// Apply the adapter to the spinner
        spinner2.adapter = ageAdapter

// Set item selection listener for spinner2 (Age)
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedAge = parent.getItemAtPosition(position).toString()
                // Handle the selected age range value
                when (selectedAge) {
                    "18-24" -> {
                        // Perform actions for 18-24 age range selection
//                        Toast.makeText(this@MainActivityEdit, selectedAge, Toast.LENGTH_SHORT).show()
                    }
                    "25-34" -> {
                        // Perform actions for 25-34 age range selection
//                        Toast.makeText(this@MainActivityEdit, selectedAge, Toast.LENGTH_SHORT).show()

                    }
                    "35-44" -> {
                        // Perform actions for 35-44 age range selection
//                        Toast.makeText(this@MainActivityEdit, selectedAge, Toast.LENGTH_SHORT).show()

                    }
                    "45+" -> {
                        // Perform actions for 45+ age range selection
//                        Toast.makeText(this@MainActivityEdit, selectedAge, Toast.LENGTH_SHORT).show()

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case when nothing is selected
            }
        }


        // Add code for handling other UI components and their events as needed
    }

    private fun saveChanges() {
        // Retrieve the values from the EditText fields and perform the necessary actions
        val FullName = etFullName.text.toString()
      val Email =   etEmail.text.toString()
      val PhoneNumber=  etPhoneNumber.text.toString()
       val About =  etAbout.text.toString()
       val Interest = etInterest.text.toString()
       val Address =  etAddress.text.toString()

        // Perform actions with the retrieved values
    }
}
