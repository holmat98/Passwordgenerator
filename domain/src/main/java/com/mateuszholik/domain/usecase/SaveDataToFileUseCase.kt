package com.mateuszholik.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import com.mateuszholik.domain.models.DataToSave
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import java.time.LocalDateTime

internal interface SaveDataToFileUseCase : CompletableUseCase<DataToSave>

internal class SaveDataToFileUseCaseImpl(
    private val context: Context
) : SaveDataToFileUseCase {

    override fun invoke(param: DataToSave): Completable =
        Completable.fromCallable {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, FILE_NAME)
                put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                contentValues
            )

            uri?.let {
                val outputStream = context.contentResolver.openOutputStream(it)
                outputStream?.run {
                    write(param.data.toByteArray())
                    close()
                }
            }
        }

    private companion object {
        val FILE_NAME = "${LocalDateTime.now()}_passwords"
        const val MIME_TYPE = "text/plain"
    }
}