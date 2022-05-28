package com.mateuszholik.passwordgenerator.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.managers.NotificationManager
import com.mateuszholik.passwordgenerator.utils.WorkersInputDataKeys.NOTIFICATION_ID_KEY
import com.mateuszholik.passwordgenerator.utils.WorkersInputDataKeys.PLATFORM_NAME_KEY
import org.koin.core.KoinComponent
import org.koin.core.inject

class NotificationWorker(
    val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters), KoinComponent {

    private val notificationManager: NotificationManager by inject()

    override fun doWork(): Result {
        val platformName = inputData.getString(PLATFORM_NAME_KEY)
        val notificationId = inputData.getInt(NOTIFICATION_ID_KEY, 0)

        notificationManager.showNotification(
            R.drawable.ic_launcher_foreground,
            context.getString(R.string.notification_worker_title),
            context.getString(R.string.notification_worker_message, platformName),
            notificationId
        )

        return Result.success()
    }

    companion object {
        const val NOTIFICATION_WORKER_TAG = "NOTIFICATION_WORKER_TAG"
    }
}