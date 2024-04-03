package com.sandhyagill.fbnotification

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import com.sandhyagill.fbnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowNotification.setOnClickListener {
            Notification.Builder(this)
                .setContentTitle("This is Title")
                .setContentText("This is message")
        }
    }
}