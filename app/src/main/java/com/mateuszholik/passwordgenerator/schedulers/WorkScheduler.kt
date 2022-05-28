package com.mateuszholik.passwordgenerator.schedulers

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.utils.WorkersInputDataKeys.NOTIFICATION_ID_KEY
import com.mateuszholik.passwordgenerator.utils.WorkersInputDataKeys.PLATFORM_NAME_KEY
import com.mateuszholik.passwordgenerator.workers.NotificationWorker
import com.mateuszholik.passwordgenerator.workers.NotificationWorker.Companion.NOTIFICATION_WORKER_TAG
import java.util.concurrent.TimeUnit

interface WorkScheduler {

    fun schedule(data: Password, delay: Long)

    fun cancelWorker(id: Long)
}

class NotificationWorkSchedulerImpl(
    private val context: Context,
    private val messageProvider: MessageProvider
) : WorkScheduler {

    override fun schedule(data: Password, delay: Long) {
        val inputData: Data = workDataOf(
            PLATFORM_NAME_KEY to data.platformName,
            NOTIFICATION_ID_KEY to data.id.toInt()
        )

        val tag = NOTIFICATION_WORKER_TAG + data.id

        val work = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(
                delay,
                TimeUnit.MILLISECONDS
            )
            .addTag(tag)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).run {
            cancelAllWorkByTag(tag)
            enqueue(work)
        }
        messageProvider.show(R.string.password_details_notification_set)
    }

    override fun cancelWorker(id: Long) {
        WorkManager.getInstance(context).cancelAllWorkByTag(NOTIFICATION_WORKER_TAG + id)
    }
}