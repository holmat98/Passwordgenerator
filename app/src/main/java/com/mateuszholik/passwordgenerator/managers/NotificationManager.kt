package com.mateuszholik.passwordgenerator.managers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

interface NotificationManager {

    fun showNotification(
        @DrawableRes smallIcon: Int,
        title: String,
        message: String,
        notificationId: Int,
        pendingIntent: PendingIntent? = null,
    )
}

class NotificationManagerImpl(
    private val context: Context,
) : NotificationManager {

    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    @SuppressLint("MissingPermission")
    override fun showNotification(
        smallIcon: Int,
        title: String,
        message: String,
        notificationId: Int,
        pendingIntent: PendingIntent?,
    ) {
        if (notificationManager.areNotificationsEnabled()) {
            createNotificationChannel()

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(smallIcon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(pendingIntent != null)
                .build()

            notificationManager.notify(notificationId, notification)
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            android.app.NotificationManager.IMPORTANCE_HIGH
        )

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        manager.createNotificationChannel(channel)
    }

    private companion object {
        const val CHANNEL_ID = "1"
        const val CHANNEL_NAME = "Expired passwords"
    }
}
