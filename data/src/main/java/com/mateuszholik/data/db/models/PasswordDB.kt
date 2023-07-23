package com.mateuszholik.data.db.models

internal data class PasswordDB(
    val platformName: ByteArray,
    val platformNameIv: ByteArray,
    val password: ByteArray,
    val passwordIv: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PasswordDB

        if (!platformName.contentEquals(other.platformName)) return false
        if (!platformNameIv.contentEquals(other.platformNameIv)) return false
        if (!password.contentEquals(other.password)) return false
        if (!passwordIv.contentEquals(other.passwordIv)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = platformName.contentHashCode()
        result = 31 * result + platformNameIv.contentHashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + passwordIv.contentHashCode()
        return result
    }
}
