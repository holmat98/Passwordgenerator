package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapper.UpdatedPassword
import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapper.UpdatedName

internal interface UpdatedPasswordToUpdatedNamesMapper : Mapper<UpdatedPassword, UpdatedName> {

    data class UpdatedPassword(
        val name: String,
        val website: String?
    )

    data class UpdatedName(
        val name: ByteArray,
        val nameIv: ByteArray,
        val website: ByteArray?,
        val websiteIv: ByteArray?
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UpdatedName

            if (!name.contentEquals(other.name)) return false
            if (!nameIv.contentEquals(other.nameIv)) return false
            if (website != null) {
                if (other.website == null) return false
                if (!website.contentEquals(other.website)) return false
            } else if (other.website != null) return false
            if (websiteIv != null) {
                if (other.websiteIv == null) return false
                if (!websiteIv.contentEquals(other.websiteIv)) return false
            } else if (other.websiteIv != null) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.contentHashCode()
            result = 31 * result + nameIv.contentHashCode()
            result = 31 * result + (website?.contentHashCode() ?: 0)
            result = 31 * result + (websiteIv?.contentHashCode() ?: 0)
            return result
        }

    }
}

internal class UpdatedPasswordToUpdatedNamesMapperImpl(
    private val encryptionManager: KeyBaseEncryptionManager
) : UpdatedPasswordToUpdatedNamesMapper {

    override fun map(param: UpdatedPassword): UpdatedName {
        val encryptedPlatformName = encryptionManager.encrypt(param.name)
        val encryptedWebsite = param.website?.let { encryptionManager.encrypt(it) }

        return UpdatedName(
            name = encryptedPlatformName.data,
            nameIv = encryptedPlatformName.iv,
            website = encryptedWebsite?.data,
            websiteIv = encryptedWebsite?.iv
        )
    }
}
