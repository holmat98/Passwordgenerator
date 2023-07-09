package com.mateuszholik.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mateuszholik.data.db.models.NamesEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
internal data class NamesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME) val platformName: ByteArray,
    @ColumnInfo(name = COLUMN_NAME_IV) val platformNameIv: ByteArray,
    @ColumnInfo(name = COLUMN_PACKAGE_NAME) val packageName: ByteArray? = null,
    @ColumnInfo(name = COLUMN_PACKAGE_NAME_IV) val packageNameIv: ByteArray? = null,
    @ColumnInfo(name = COLUMN_WEBSITE) val website: ByteArray? = null,
    @ColumnInfo(name = COLUMN_WEBSITE_IV) val websiteIv: ByteArray? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NamesEntity

        if (id != other.id) return false
        if (!platformName.contentEquals(other.platformName)) return false
        if (!platformNameIv.contentEquals(other.platformNameIv)) return false
        if (!packageName.contentEquals(other.packageName)) return false
        if (!packageNameIv.contentEquals(other.packageNameIv)) return false
        if (!website.contentEquals(other.website)) return false
        if (!websiteIv.contentEquals(other.websiteIv)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + platformName.contentHashCode()
        result = 31 * result + platformNameIv.contentHashCode()
        result = 31 * result + packageName.contentHashCode()
        result = 31 * result + packageNameIv.contentHashCode()
        result = 31 * result + website.contentHashCode()
        result = 31 * result + websiteIv.contentHashCode()
        return result
    }

    companion object {
        const val TABLE_NAME = "names"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "column_name"
        const val COLUMN_NAME_IV = "column_name_iv"
        const val COLUMN_PACKAGE_NAME = "package_name"
        const val COLUMN_PACKAGE_NAME_IV = "package_name_iv"
        const val COLUMN_WEBSITE = "website"
        const val COLUMN_WEBSITE_IV = "website_iv"
    }
}
