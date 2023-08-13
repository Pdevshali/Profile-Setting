package com.example.profilesetting

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.properties.Delegates

class ShowMatch_Activity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    private lateinit var myAdapter: MatchAdapter


    private var userLatitude by Delegates.notNull<Double>()
    private var userLongitude by Delegates.notNull<Double>()
    private val filteredUsersList = mutableListOf<UserProfile>()
    private lateinit var db : FirebaseFirestore
    private lateinit var firebaseAuth : FirebaseAuth
    private var currentUserId: String? = null
    lateinit var progressBar: ProgressBar


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_match)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progress_bar)


        progressBar.visibility = View.VISIBLE


        recyclerView = findViewById(R.id.RvMatch)
        myAdapter = MatchAdapter(filteredUsersList)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        fetchUserLocation {
            // Once the user's location is fetched, call a function to fetch and display filtered users based on location
            fetchAndDisplayFilteredUsers()
            progressBar.visibility = View.GONE

        }

    }


    private fun fetchUserLocation(onSuccess: () -> Unit) {
        // Get the current user's ID
        val userId =firebaseAuth.currentUser?.uid

        // Get the Firestore reference
        val usersCollection = db.collection("Users")

        // Fetch the current user's document from Firestore
        userId?.let { uid ->
            usersCollection.document(uid).get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get the user's latitude and longitude from the document
                    userLatitude = documentSnapshot.getDouble("latitude") ?: 0.0
                    userLongitude = documentSnapshot.getDouble("longitude") ?: 0.0

                    // Call the success callback
                    onSuccess()
                } else {
                    // Handle the case when the user document does not exist
                    // For example, display an error message or take appropriate action
                    Toast.makeText(this, "User not exist", Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener { exception ->
                // Handle the fetch failure
                // Show an error message or take appropriate action
                exception.message?.let { exception -> Log.d(ContentValues.TAG, exception) }
            }
        }
    }

    private fun fetchAndDisplayFilteredUsers() {
        // Perform a geospatial query to fetch filtered users from the database
        // You can use the Firestore geospatial query example from the previous response or any other method based on your chosen database
        val allUsers = mutableListOf<UserProfile>()

// Get a reference to the "Users" collection in Firestore
        val usersCollection = db.collection("Users")
        currentUserId = firebaseAuth.currentUser?.uid


// Perform a query to get all documents in the "Users" collection
        usersCollection.get()
            .addOnSuccessListener { querySnapshot ->
                // Loop through the documents and convert them to UserProfile objects
                for (document in querySnapshot.documents) {
                    val name = document.getString("name") ?: ""
                    val location = document.getString("Location") ?: ""
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val latitude = document.getDouble("latitude") ?: 0.0
                    val longitude = document.getDouble("longitude") ?: 0.0
                    val id = document.id // Get the document ID (user ID)

                    if (id == currentUserId) {
                        continue
                    }
//                    for debugging the code:
//                    Log.d(ContentValues.TAG, "user is: $id")
//                    Log.d(ContentValues.TAG, "CurrentUser is: $currentUserId")

                    // Create a UserProfile object and add it to the list
                    val userProfile = UserProfile(name, location, imageUrl, latitude, longitude, id)
                    allUsers.add(userProfile)
                }


                // Filter users based on their locations
                filteredUsersList.clear() // Clear the list before adding filtered users
                for (user in allUsers) {
                    // Check if the user's location is within the desired range
                    val distance = calculateDistance(user.latitude, user.longitude, userLatitude, userLongitude)
                    if (distance <= 10.0) { // Assume 10 km as the desired range for demonstration
                        filteredUsersList.add(user)
                    }
                }

                // Once you have the list of filtered users, update the RecyclerView adapter
                // with the new list to display the filtered users in the RecyclerView
                (recyclerView.adapter as MatchAdapter).updateData(filteredUsersList)
            }
    }



    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadiusKm = 6371 // Earth's radius in kilometers

        // Convert latitude and longitude from degrees to radians
        val lat1Rad = Math.toRadians(lat1)
        val lon1Rad = Math.toRadians(lon1)
        val lat2Rad = Math.toRadians(lat2)
        val lon2Rad = Math.toRadians(lon2)

        // Calculate the differences between latitudes and longitudes
        val latDiff = lat2Rad - lat1Rad
        val lonDiff = lon2Rad - lon1Rad

        // Calculate the distance using the Haversine formula
        val a = sin(latDiff / 2) * sin(latDiff / 2) +

                cos(lat1Rad) * cos(lat2Rad) *
                sin(lonDiff / 2) * sin(lonDiff / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadiusKm * c // Distance in kilometers
    }


}