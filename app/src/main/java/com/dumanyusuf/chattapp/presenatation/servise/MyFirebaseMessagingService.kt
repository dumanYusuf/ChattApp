package com.dumanyusuf.chattapp.presenatation.servise

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService:FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
            Log.e("🔥FCM", "✅ Mesaj alındı: ${message.data}")

        message.notification?.let {
            Log.d("🔥FCM", "📩 Bildirim başlık: ${it.title}, içerik: ${it.body}")
            showNotification(it.title ?: "Bildirim", it.body ?: "Yeni bir mesajınız var.")
        }

        if (message.data.isNotEmpty()) {
            Log.d("🔥FCM", "📦 Data payload: ${message.data}")
        }
    }

    private fun showNotification(title: String, messageBody: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel"
            val channelName = "Default Channel"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 1
        val notification = NotificationCompat.Builder(this, "default_channel")
            .setContentTitle(title)
            .setContentText(messageBody)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "🆕 Yeni token oluştu: $token")
    }
}