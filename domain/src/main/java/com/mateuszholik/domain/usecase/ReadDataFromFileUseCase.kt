package com.mateuszholik.domain.usecase

import android.content.Context
import android.net.Uri
import com.mateuszholik.domain.usecase.base.ParameterizedMaybeUseCase
import io.reactivex.rxjava3.core.Maybe

internal interface ReadDataFromFileUseCase : ParameterizedMaybeUseCase<Uri, String>

internal class ReadDataFromFileUseCaseImpl(
    private val context: Context
) : ReadDataFromFileUseCase {

    override fun invoke(param: Uri): Maybe<String> =
        Maybe.fromCallable {
            val inputStream = context.contentResolver.openInputStream(param)
            val data = inputStream?.readBytes()
            inputStream?.close()

            data?.let { String(it) }
        }
}
