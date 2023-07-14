package com.example.profilesetting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat

class MainActivityNotification : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchSound: Switch
    private lateinit var switchVibrate: Switch
    private lateinit var switchNewTips: Switch
    private lateinit var switchNewService: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_setting)

        switchSound = findViewById(R.id.switchSound)
        switchVibrate = findViewById(R.id.switchvibrate)
        switchNewTips = findViewById(R.id.switchNewTips)
        switchNewService = findViewById(R.id.switchNewservice)

        // Set listener for switchSound
        switchSound.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // Sound is enabled
//                // Perform necessary actions
//            } else {
//                // Sound is disabled
//                // Perform necessary actions
//            }
        }

        // Set listener for switchVibrate
        switchVibrate.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // Vibration is enabled
//                // Perform necessary actions
//            } else {
//                // Vibration is disabled
//                // Perform necessary actions
//            }
        }

        // Set listener for switchNewTips
        switchNewTips.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // New tips notification is enabled
//                // Perform necessary actions
//            } else {
//                // New tips notification is disabled
//                // Perform necessary actions
//            }
        }

        // Set listener for switchNewService
        switchNewService.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // New service notification is enabled
//                // Perform necessary actions
//            } else {
//                // New service notification is disabled
//                // Perform necessary actions
//            }
//        }

        }
    }
}