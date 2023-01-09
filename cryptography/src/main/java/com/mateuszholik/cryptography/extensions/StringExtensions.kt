package com.mateuszholik.cryptography.extensions

import com.mateuszholik.cryptography.models.EncryptedData

internal fun String.convertToByteArray(): ByteArray {
    val bytes = this.split(' ').map { it.toInt().toByte() }

    return ByteArray(bytes.size) { bytes[it] }
}

fun String.toEncryptedData(): EncryptedData {
    val (data, iv) = this.split("\n")

    return EncryptedData(
        iv = iv.convertToByteArray(),
        data = data.convertToByteArray()
    )
}

internal fun String.adjustLengthAndReturnByteArray(intendedLength: Int): ByteArray {
    var temp = this
    while (temp.toByteArray().size % intendedLength != 0) {
        temp += "\u0020"
    }

    return temp.toByteArray(Charsets.UTF_8)
}