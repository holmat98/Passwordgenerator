package com.mateuszholik.data.cryptography.extensions

import com.mateuszholik.data.cryptography.Utils.IV_SEPARATOR
import com.mateuszholik.data.cryptography.models.EncryptedData
import java.lang.IllegalArgumentException

internal fun String.toEncryptedData(): EncryptedData {
    val splitedData = this.split(IV_SEPARATOR)

    if (splitedData.size != 2) throw IllegalArgumentException()

    val iv = splitedData[0].toByteArray(Charsets.ISO_8859_1)
    val encryptedText = splitedData[1].toByteArray(Charsets.ISO_8859_1)

    return EncryptedData(iv, encryptedText)
}