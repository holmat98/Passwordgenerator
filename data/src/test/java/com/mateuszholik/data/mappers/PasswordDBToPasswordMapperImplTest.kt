package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PasswordDBToPasswordMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager>()

    private val passwordDBToPasswordMapper = PasswordDBToPasswordMapperImpl(
        encryptionManager = encryptionManager
    )

    @Test
    fun `PasswordDB object is correctly mapped to the Password object`() {
        every {
            encryptionManager.decrypt(ENCRYPTED_DATA_PASSWORD)
        } returns PASSWORD

        every {
            encryptionManager.decrypt(ENCRYPTED_DATA_PLATFORM_NAME)
        } returns PLATFORM_NAME

        val result = passwordDBToPasswordMapper.map(
            PasswordDB(
                platformName = ENCRYPTED_PLATFORM_NAME,
                platformNameIv = IV,
                password = ENCRYPTED_PASSWORD,
                passwordIv = IV
            )
        )

        assertThat(result).isEqualTo(
            Password(
                platformName = PLATFORM_NAME,
                password = PASSWORD
            )
        )
    }

    private companion object {
        const val PASSWORD = "password"
        const val PLATFORM_NAME = "platformName"
        val IV = ByteArray(5) { it.toByte() }
        val ENCRYPTED_PASSWORD = PASSWORD.toByteArray()
        val ENCRYPTED_PLATFORM_NAME = PLATFORM_NAME.toByteArray()
        val ENCRYPTED_DATA_PLATFORM_NAME = EncryptedData(
            iv = IV,
            data = ENCRYPTED_PLATFORM_NAME
        )
        val ENCRYPTED_DATA_PASSWORD = EncryptedData(
            iv = IV,
            data = ENCRYPTED_PASSWORD
        )
    }

}
