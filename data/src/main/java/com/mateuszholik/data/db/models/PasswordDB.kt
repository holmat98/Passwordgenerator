package com.mateuszholik.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "passwords")
internal data class PasswordDB(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val platformName: ByteArray,
    val platformIV: ByteArray,
    val password: ByteArray,
    val passwordIV: ByteArray,
    val expiringDate: LocalDateTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PasswordDB

        if (id != other.id) return false
        if (!platformName.contentEquals(other.platformName)) return false
        if (!platformIV.contentEquals(other.platformIV)) return false
        if (!password.contentEquals(other.password)) return false
        if (!passwordIV.contentEquals(other.passwordIV)) return false
        if (expiringDate != other.expiringDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + platformName.contentHashCode()
        result = 31 * result + platformIV.contentHashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + passwordIV.contentHashCode()
        result = 31 * result + expiringDate.hashCode()
        return result
    }
}