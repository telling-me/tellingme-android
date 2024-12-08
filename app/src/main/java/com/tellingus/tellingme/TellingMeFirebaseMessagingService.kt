package com.tellingus.tellingme

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tellingus.tellingme.presentation.ui.feature.MainActivity

class TellingMeFirebaseMessagingService : FirebaseMessagingService() {
    private val NOTIFICATION_CHANNEL_ID = "tellingus_tellingme"
    private val NOTIFICATION_CHANNEL_NAME = "Notification"
    private val NOTIFICATION_CHANNEL_DESCRIPTION = "notification channel"
    var TAG: String = "로그"

    // 메세지 수신
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "remoteMessage.data: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            Log.d("taag", "remoteMessage.notification: ${it.title}, ${it.body} ")

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.tellingme_logo)
                .setContentTitle(it.title)
                .setContentText(it.body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0, notificationBuilder.build())
        }
    }
}