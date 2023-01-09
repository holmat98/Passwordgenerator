package com.mateuszholik.domain.usecase

import android.content.Context
import com.mateuszholik.domain.models.DataToSave
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

internal interface SaveDataToFileUseCase : CompletableUseCase<DataToSave>

internal class SaveDataToFileUseCaseImpl(
    private val context: Context
) : SaveDataToFileUseCase {

    override fun invoke(param: DataToSave): Completable =
        Completable.fromCallable {
            param.uri?.let {
                val outputStream = context.contentResolver.openOutputStream(it)
                outputStream?.run {
                    write(param.data.toByteArray())
                    close()
                }
            }
        }
}