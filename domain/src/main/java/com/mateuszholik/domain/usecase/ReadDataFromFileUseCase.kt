package com.mateuszholik.domain.usecase

import android.content.Context
import android.net.Uri
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

internal interface ReadDataFromFileUseCase : ParameterizedSingleUseCase<Uri, String>

internal class ReadDataFromFileUseCaseImpl(
    private val context: Context
) : ReadDataFromFileUseCase {

    override fun invoke(param: Uri): Single<String> =
        Single.fromCallable {
            val inputStream = context.contentResolver.openInputStream(param)
            val data = inputStream?.readBytes()
            inputStream?.close()

            data?.let { String(it) }
        }
}