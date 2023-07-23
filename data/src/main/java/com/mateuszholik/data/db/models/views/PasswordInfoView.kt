package com.mateuszholik.data.db.models.views

import androidx.room.DatabaseView
import java.time.LocalDateTime

@DatabaseView(
    """
        SELECT 
            new_passwords.id as id,
            names.name as platformName,
            names.name_iv as platformNameIv,
            new_passwords.password_score as passwordScore,
            new_passwords.expiration_date as expirationDate
        FROM new_passwords
        JOIN names ON new_passwords.name_id = names.id
    """
)
internal data class PasswordInfoView(
    val id: Long,
    val platformName: ByteArray,
    val platformNameIv: ByteArray,
    val passwordScore: Int,
    val expirationDate: LocalDateTime?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PasswordInfoView

        if (id != other.id) return false
        if (!platformName.contentEquals(other.platformName)) return false
        if (!platformNameIv.contentEquals(other.platformNameIv)) return false
        if (passwordScore != other.passwordScore) return false
        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + platformName.contentHashCode()
        result = 31 * result + platformNameIv.contentHashCode()
        result = 31 * result + passwordScore
        result = 31 * result + expirationDate.hashCode()
        return result
    }
}
