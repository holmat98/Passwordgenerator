package com.mateuszholik.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mateuszholik.data.db.models.PasswordEntity.Companion.COLUMN_NAME_ID
import com.mateuszholik.data.db.models.PasswordEntity.Companion.TABLE_NAME
import java.time.LocalDateTime

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = NamesEntity::class,
            childColumns = [COLUMN_NAME_ID],
            parentColumns = [NamesEntity.COLUMN_ID]
        )
    ]
)
internal data class PasswordEntity(
    @ColumnInfo(name = COLUMN_ID_NAME)
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME_ID) val nameId: Long,
    @ColumnInfo(name = COLUMN_PASSWORD_NAME) val password: ByteArray,
    @ColumnInfo(name = COLUMN_PASSWORD_IV_NAME) val passwordIV: ByteArray,
    @ColumnInfo(name = COLUMN_EXPIRATION_DATE_NAME) val expirationDate: LocalDateTime,
    @ColumnInfo(name = COLUMN_PASSWORD_SCORE) val passwordScore: Int,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PasswordEntity

        if (id != other.id) return false
        if (nameId != other.nameId) return false
        if (!password.contentEquals(other.password)) return false
        if (!passwordIV.contentEquals(other.passwordIV)) return false
        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nameId.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + passwordIV.contentHashCode()
        result = 31 * result + expirationDate.hashCode()
        return result
    }

    companion object {
        const val TABLE_NAME = "new_passwords"
        const val COLUMN_ID_NAME = "id"
        const val COLUMN_NAME_ID = "name_id"
        const val COLUMN_PASSWORD_NAME = "password"
        const val COLUMN_PASSWORD_IV_NAME = "password_iv"
        const val COLUMN_EXPIRATION_DATE_NAME = "expiration_date"
        const val COLUMN_PASSWORD_SCORE = "password_score"
    }
}
