package com.mateuszholik.passwordgenerator.schedulers

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import androidx.work.ExistingWorkPolicy
import com.mateuszholik.passwordgenerator.utils.WorkersInputDataKeys.PASSWORD_ID
import com.mateuszholik.passwordgenerator.workers.NotificationWorker
import com.mateuszholik.passwordgenerator.workers.NotificationWorker.Companion.NOTIFICATION_WORKER_TAG
import java.util.concurrent.TimeUnit

interface WorkScheduler {

    fun schedule(passwordId: Long, delay: Long)

    fun cancelWorker(id: Long)
}

class NotificationWorkSchedulerImpl(
    private val context: Context
) : WorkScheduler {

    override fun schedule(passwordId: Long, delay: Long) {
        val inputData: Data = workDataOf(
            PASSWORD_ID to passwordId
        )

        val uniqueWorkName = NOTIFICATION_WORKER_TAG + passwordId

        val work = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            uniqueWorkName,
            ExistingWorkPolicy.REPLACE,
            work
        )
    }

    override fun cancelWorker(id: Long) {
        WorkManager.getInstance(context).cancelAllWorkByTag(NOTIFICATION_WORKER_TAG + id)
    }
}