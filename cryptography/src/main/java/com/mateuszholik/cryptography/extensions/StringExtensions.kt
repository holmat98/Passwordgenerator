package com.mateuszholik.cryptography.extensions

import com.mateuszholik.cryptography.models.EncryptedData

fun String.toEncryptedData(): EncryptedData {
    val (data, iv) = this.split("\n")

    return EncryptedData(iv = iv.toByteArray(), data = data.toByteArray())
}

internal fun String.adjustLengthAndReturnByteArray(intendedLength: Int): ByteArray {
    var temp = this
    while (temp.toByteArray().size % intendedLength != 0) {
        temp += "\u0020"
    }

    return temp.toByteArray(Charsets.UTF_8)
}