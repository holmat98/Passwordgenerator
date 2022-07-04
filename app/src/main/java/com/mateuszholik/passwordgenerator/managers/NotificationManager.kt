package com.mateuszholik.passwordgenerator.managers

import android.app.NotificationChannel
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

interface NotificationManager {

    fun showNotification(
        @DrawableRes smallIcon: Int,
        title: String,
        message: String,
        notificationId: Int
    )
}

class NotificationManagerImpl(
    private val context: Context
) : NotificationManager {

    override fun showNotification(
        smallIcon: Int,
        title: String,
        message: String,
        notificationId: Int
    ) {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(smallIcon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(notificationId, notification)
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
        const val CHANNEL_NAME = "NotificationChannel"
    }
}