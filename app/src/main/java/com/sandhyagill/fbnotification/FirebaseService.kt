package com.sandhyagill.fbnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Calendar

class FirebaseService : FirebaseMessagingService() {
    private val TAG = "FirebaseService"
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        var notificationData = message.notification
        Log.e(TAG,"notification title ${notificationData?.title}")
        Log.e(TAG,"notification body ${notificationData?.body}")

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            notificationManager.createNotificationChannel(channel)
        }

        var builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationData?.title?:"")
            .setContentText(notificationData?.body?:"")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

//        var imageUrl = "https://www.pakainfo.com"
//        imageUrl?.let {
//            val bitmap = BitmapFactory.decodeStream(java.net.URL(imageUrl).openConnection().getInputStream())
//            val bigPictureStyle = NotificationCompat.BigPictureStyle()
//                .bigPicture(bitmap)
////                .bigLargeIcon(null) // Clear the large icon to display only the image
//
//            builder.setStyle(bigPictureStyle)
//        }
        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), builder.build())
}
    override fun onNewToken(token: String) {
        Log.e(TAG, "on new token $token")
    }
}