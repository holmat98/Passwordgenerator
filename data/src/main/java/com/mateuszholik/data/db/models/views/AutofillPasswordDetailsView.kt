package com.mateuszholik.data.db.models.views

import androidx.room.DatabaseView

@DatabaseView(
    """
        SELECT
            new_passwords.id as id,
            names.name as platformName,
            names.name_iv as platformNameIv,
            new_passwords.password as password,
            new_passwords.password_iv as passwordIv,
            names.package_name as packageName,
            names.package_name_iv as packageNameIv
        FROM new_passwords
        JOIN names ON new_passwords.name_id = names.id
    """
)
internal data class AutofillPasswordDetailsView(
    val id: Long,
    val platformName: ByteArray,
    val platformNameIv: ByteArray,
    val password: ByteArray,
    val passwordIv: ByteArray,
    val packageName: ByteArray?,
    val packageNameIv: ByteArray?,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AutofillPasswordDetailsView

        if (id != other.id) return false
        if (!platformName.contentEquals(other.platformName)) return false
        if (!platformNameIv.contentEquals(other.platformNameIv)) return false
        if (!password.contentEquals(other.password)) return false
        if (!passwordIv.contentEquals(other.passwordIv)) return false
        if (packageName != null) {
            if (other.packageName == null) return false
            if (!packageName.contentEquals(other.packageName)) return false
        } else if (other.packageName != null) return false
        if (packageNameIv != null) {
            if (other.packageNameIv == null) return false
            if (!packageNameIv.contentEquals(other.packageNameIv)) return false
        } else if (other.packageNameIv != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + platformName.contentHashCode()
        result = 31 * result + platformNameIv.contentHashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + passwordIv.contentHashCode()
        result = 31 * result + (packageName?.contentHashCode() ?: 0)
        result = 31 * result + (packageNameIv?.contentHashCode() ?: 0)
        return result
    }
}
