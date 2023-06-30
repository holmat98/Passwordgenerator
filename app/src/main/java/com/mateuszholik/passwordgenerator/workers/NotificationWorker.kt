package com.mateuszholik.passwordgenerator.workers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.managers.NotificationManager
import com.mateuszholik.passwordgenerator.ui.MainActivity
import com.mateuszholik.passwordgenerator.utils.WorkersInputDataKeys.PASSWORD_ID
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class NotificationWorker(
    private val notificationManager: NotificationManager,
    private val getPasswordUseCase: GetPasswordUseCase,
    val context: Context,
    workerParameters: WorkerParameters,
) : RxWorker(context, workerParameters) {

    override fun createWork(): Single<Result> =
        getPasswordUseCase(inputData.getLong(PASSWORD_ID, 0L))
            .toSingle()
            .flatMap { password ->
                Completable.fromAction {
                    notificationManager.showNotification(
                        R.drawable.ic_launcher_foreground,
                        context.getString(R.string.notification_worker_title),
                        context.getString(
                            R.string.notification_worker_message,
                            password.platformName
                        ),
                        password.id.toInt(),
                        createMainActivityPendingIntent()
                    )
                }.andThen(Single.just(Result.success()))
            }
            .onErrorReturn { Result.failure() }

    private fun createMainActivityPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        return PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        const val NOTIFICATION_WORKER_TAG = "NOTIFICATION_WORKER_TAG"
        const val PENDING_INTENT_REQUEST_ID = 54321
    }
}
