package com.example.profilesetting

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch

class MainActivity_Notification : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         lateinit var switchSound: Switch
         lateinit var ImageViewIcon1: ImageView
        setContentView(R.layout.activity_notification)


        switchSound = findViewById(R.id.switchSound)
        ImageViewIcon1 = findViewById(R.id.imageViewIcon1)
        ImageViewIcon1.setOnClickListener {
            val intent = Intent(this, MainActivityCreateProfile::class.java)
            startActivity(intent)
        }

        // Set listener for switchSound
        switchSound.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Sound is enabled
                enableSound()
            } else {
                // Sound is disabled
                disableSound()
            }
        }
    }

    private fun enableSound() {
        // Check if the device is running on Android Oreo (API level 26) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "your_channel_id" // Replace with your desired channel ID
            val channelName = "Your Channel Name" // Replace with your desired channel name

            // Set channel importance based on whether playSound is true or false
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            // Set audio attributes for the notification sound
            val audioAttribute = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            // Get the default notification sound for alarms
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

            // Create a notification channel
            val notyChannel = NotificationChannel(channelID, channelName, channelImportance)

            // Customize the notification channel properties
            notyChannel.enableLights(true)
            notyChannel.lightColor = Color.RED
            notyChannel.setSound(notificationSound, audioAttribute)
            notyChannel.enableVibration(true)
            notyChannel.vibrationPattern = longArrayOf(
                1000, 100, 100, 100, 1000, 100, 100, 100, 1000
            )

            // Create the notification channel
            createNotificationChannel(notyChannel)
        }
    }

    private fun disableSound() {
        // TODO: Implement logic to disable sound for notifications
        // For example, you can use AudioManager to change the device's sound settings
        // AudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT)
    }

    // Function to create the notification channel (add it to the notification manager)
    private fun createNotificationChannel(channel: NotificationChannel) {
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
    }
}
