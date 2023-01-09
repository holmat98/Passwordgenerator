package com.mateuszholik.cryptography.models

import com.mateuszholik.cryptography.extensions.convertToString

data class EncryptedData(val iv: ByteArray, val data: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptedData

        if (!iv.contentEquals(other.iv)) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = iv.contentHashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(data.convertToString())
        stringBuilder.append("\n")
        stringBuilder.append(iv.convertToString())

        return stringBuilder.toString()
    }
}