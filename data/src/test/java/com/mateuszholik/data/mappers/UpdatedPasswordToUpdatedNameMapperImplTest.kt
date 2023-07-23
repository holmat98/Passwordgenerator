package com.mateuszholik.data.mappers

import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapper.UpdatedPassword
import com.mateuszholik.data.mappers.UpdatedPasswordToUpdatedNamesMapper.UpdatedName
import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UpdatedPasswordToUpdatedNameMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every { encrypt(PLATFORM_NAME) } returns ENCRYPTED_PLATFORM_NAME
        every { encrypt(WEBSITE) } returns ENCRYPTED_WEBSITE
    }

    private val updatedPasswordToUpdatedNamesMapper = UpdatedPasswordToUpdatedNamesMapperImpl(
        encryptionManager = encryptionManager
    )

    @Test
    fun `UpdatedPassword object is correctly mapped to the UpdatedName object`() {
        val result = updatedPasswordToUpdatedNamesMapper.map(
            UpdatedPassword(
                name = PLATFORM_NAME,
                website = WEBSITE
            )
        )

        assertThat(result).isEqualTo(
            UpdatedName(
                name = ENCRYPTED_PLATFORM_NAME.data,
                nameIv = ENCRYPTED_PLATFORM_NAME.iv,
                website = ENCRYPTED_WEBSITE.data,
                websiteIv = ENCRYPTED_WEBSITE.iv
            )
        )
    }

    @Test
    fun `UpdatedPassword object with website equal to null is correctly mapped to the UpdatedName object`() {
        val result = updatedPasswordToUpdatedNamesMapper.map(
            UpdatedPassword(
                name = PLATFORM_NAME,
                website = null
            )
        )

        assertThat(result).isEqualTo(
            UpdatedName(
                name = ENCRYPTED_PLATFORM_NAME.data,
                nameIv = ENCRYPTED_PLATFORM_NAME.iv,
                website = null,
                websiteIv = null
            )
        )
    }

    private companion object {
        const val PLATFORM_NAME = "platformName"
        const val WEBSITE = "website"

        val ENCRYPTED_PLATFORM_NAME = EncryptedData(
            iv = ByteArray(5) { it.toByte() },
            data = PLATFORM_NAME.toByteArray()
        )
        val ENCRYPTED_WEBSITE = EncryptedData(
            iv = ByteArray(5) { it.toByte() },
            data = WEBSITE.toByteArray()
        )
    }
}
