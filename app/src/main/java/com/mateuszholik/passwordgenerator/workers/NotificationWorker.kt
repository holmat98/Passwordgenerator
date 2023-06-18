package com.mateuszholik.passwordgenerator.workers

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import com.mateuszholik.domain.usecase.GetPasswordUseCase
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.managers.NotificationManager
import com.mateuszholik.passwordgenerator.utils.WorkersInputDataKeys.PASSWORD_ID
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationWorker(
    val context: Context,
    workerParameters: WorkerParameters
) : RxWorker(context, workerParameters), KoinComponent {

    private val notificationManager: NotificationManager by inject()
    private val getPasswordUseCase: GetPasswordUseCase by inject()

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
                        password.id.toInt()
                    )
                }.andThen(Single.just(Result.success()))
            }
            .onErrorReturn { Result.failure() }

    companion object {
        const val NOTIFICATION_WORKER_TAG = "NOTIFICATION_WORKER_TAG"
    }
}
