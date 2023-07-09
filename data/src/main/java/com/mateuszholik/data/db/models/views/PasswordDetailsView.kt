package com.mateuszholik.data.db.models.views

import java.time.LocalDateTime

internal data class PasswordDetailsView(
    val id: Long,
    val platformName: ByteArray,
    val platformNameIv: ByteArray,
    val password: ByteArray,
    val passwordIv: ByteArray,
    val website: ByteArray,
    val websiteIv: ByteArray,
    val passwordScore: Int,
    val expirationDate: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PasswordDetailsView

        if (id != other.id) return false
        if (!platformName.contentEquals(other.platformName)) return false
        if (!platformNameIv.contentEquals(other.platformNameIv)) return false
        if (!password.contentEquals(other.password)) return false
        if (!passwordIv.contentEquals(other.passwordIv)) return false
        if (!website.contentEquals(other.website)) return false
        if (!websiteIv.contentEquals(other.websiteIv)) return false
        if (passwordScore != other.passwordScore) return false
        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + platformName.contentHashCode()
        result = 31 * result + platformNameIv.contentHashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + passwordIv.contentHashCode()
        result = 31 * result + website.contentHashCode()
        result = 31 * result + websiteIv.contentHashCode()
        result = 31 * result + passwordScore
        result = 31 * result + expirationDate.hashCode()
        return result
    }
}
