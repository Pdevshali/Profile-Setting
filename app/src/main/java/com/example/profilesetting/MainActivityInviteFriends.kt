package com.example.profilesetting

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivityInviteFriends : AppCompatActivity() {
    private lateinit var inviteButton: Button
    private lateinit var inviteText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invitefriends)
        inviteButton = findViewById(R.id.btInvite)
        inviteText = findViewById(R.id.tvInviteFriends)

        inviteButton.setOnClickListener {
            changeInviteStatus()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeInviteStatus() {
        inviteText.text = "Invited"
        inviteText.setTextColor(Color.parseColor("#FFC0CB")) // Watermelon color

        inviteButton.isEnabled = false
        inviteButton.visibility = View.INVISIBLE
    }
}
