package com.sandhyagill.fbnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import com.sandhyagill.fbnotification.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val CHANNEL_ID = "Channel ID"
    private  val TAG = "MainActivity"
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val NOTIFICATION_ID = Calendar.getInstance().timeInMillis.toInt()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowNotification.setOnClickListener {
           sendNotification(5000)
        }
        binding.btnCancleNotification.setOnClickListener{
            cancelNotification()
        }
    }
//    private suspend fun loadImageFromUrl(imageUrl: String): Bitmap? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val inputStream = java.net.URL(imageUrl).openStream()
//                BitmapFactory.decodeStream(inputStream)
//            } catch (e: Exception) {
//                null
//            }
//        }
//    }

    private fun sendNotification(delayMillis: Long){

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.image1, null)
        val bitmapDrawable = drawable as BitmapDrawable
        val largeIcon = bitmapDrawable.bitmap
        
        CoroutineScope(Dispatchers.Main).launch {
            delay(delayMillis)
            createNotificationChannel()
            val largeText = "This is large text enter in the notification"
            var builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("This is Title")
                .setContentText("This is content")
                .setStyle(NotificationCompat.BigTextStyle().bigText(largeText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

//            val imageUrl = "https://www.pakainfo.com"
//            imageUrl?.let {
//                val bitmap = loadImageFromUrl(imageUrl)
//                bitmap?.let {
//                    val bigPictureStyle = NotificationCompat.BigPictureStyle()
//                        .bigPicture(it)
//                    builder.setStyle(bigPictureStyle)
//                }
//            }
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun cancelNotification() {
        Log.e(TAG,"CancelNotification , Canceling notification with ID: $NOTIFICATION_ID")
        notificationManager.cancel(NOTIFICATION_ID)
    }
}
