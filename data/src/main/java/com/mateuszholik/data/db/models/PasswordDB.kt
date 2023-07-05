package com.mateuszholik.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mateuszholik.data.db.models.PasswordDB.Companion.TABLE_NAME
import java.time.LocalDateTime

@Entity(tableName = TABLE_NAME)
internal data class PasswordDB(
    @ColumnInfo(name = COLUMN_ID_NAME)
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COLUMN_PLATFORM_NAME) val platform: String,
    @ColumnInfo(name = COLUMN_PLATFORM_NAME_NAME) val platformName: ByteArray,
    @ColumnInfo(name = COLUMN_PLATFORM_IV_NAME) val platformIV: ByteArray,
    @ColumnInfo(name = COLUMN_PASSWORD_NAME) val password: ByteArray,
    @ColumnInfo(name = COLUMN_PASSWORD_IV_NAME) val passwordIV: ByteArray,
    @ColumnInfo(name = COLUMN_PASSWORD_SCORE_NAME) val passwordScore: Int,
    @ColumnInfo(name = COLUMN_WEBSITE_NAME) val website: String,
    @ColumnInfo(name = COLUMN_EXPIRATION_DATE_NAME) val expirationDate: LocalDateTime,
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
        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + platformName.contentHashCode()
        result = 31 * result + platformIV.contentHashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + passwordIV.contentHashCode()
        result = 31 * result + expirationDate.hashCode()
        return result
    }

    companion object {
        const val TABLE_NAME = "passwords"
        const val COLUMN_ID_NAME = "id"
        const val COLUMN_PLATFORM_NAME = "platform_name"
        const val COLUMN_PLATFORM_NAME_NAME = "platformName"
        const val COLUMN_PLATFORM_IV_NAME = "platformIV"
        const val COLUMN_PASSWORD_NAME = "password"
        const val COLUMN_PASSWORD_IV_NAME = "passwordIV"
        const val COLUMN_EXPIRATION_DATE_NAME = "expiringDate"
        const val COLUMN_PASSWORD_SCORE_NAME = "password_score"
        const val COLUMN_WEBSITE_NAME = "website"
    }
}
