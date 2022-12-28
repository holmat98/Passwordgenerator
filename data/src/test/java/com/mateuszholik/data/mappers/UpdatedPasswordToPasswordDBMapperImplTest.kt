package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.managers.io.SharedPrefKeys
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class UpdatedPasswordToPasswordDBMapperImplTest {

    private val encryptionManager = mockk<EncryptionManager>()
    private val sharedPrefManager = mockk<SharedPrefManager> {
        every {
            readLong(SharedPrefKeys.PASSWORD_VALIDITY, any())
        } returns PASSWORD_VALIDITY_IN_DAYS
    }
    private val updatedPasswordToPasswordDBMapper = UpdatedPasswordToPasswordDBMapperImpl(
        encryptionManager = encryptionManager,
        sharedPrefManager = sharedPrefManager
    )

    @BeforeEach
    fun setUp() {
        mockkStatic(LocalDateTime.now()::class)
        every { LocalDateTime.now() } returns TODAY_DATE
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(LocalDateTime.now()::class)
    }

    @Test
    fun `For given updatedPassword mapper will properly map it to PasswordDB model`() {
        every {
            encryptionManager.encrypt(PASSWORD)
        } returns ENCRYPTED_PASSWORD

        every {
            encryptionManager.encrypt(PLATFORM_NAME)
        } returns ENCRYPTED_PLATFORM_NAME

        val result = updatedPasswordToPasswordDBMapper.map(TESTED_VALUE)

        assertThat(result).isEqualTo(
            PasswordDB(
                id = ID,
                platformName = ENCRYPTED_PLATFORM_NAME.data,
                platformIV = ENCRYPTED_PLATFORM_NAME.iv,
                password = ENCRYPTED_PASSWORD.data,
                passwordIV = ENCRYPTED_PASSWORD.iv,
                expiringDate = TODAY_DATE.plusDays(PASSWORD_VALIDITY_IN_DAYS)
            )
        )
    }

    private companion object {
        val TODAY_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        const val PASSWORD_VALIDITY_IN_DAYS = 30L
        const val ID = 10L
        const val PASSWORD = "password"
        const val PLATFORM_NAME = "platform"
        val TESTED_VALUE = UpdatedPassword(
            id = ID,
            password = PASSWORD,
            platformName = PLATFORM_NAME
        )
        val ENCRYPTED_PASSWORD = EncryptedData(
            iv = "passwordIV".toByteArray(),
            data = PASSWORD.toByteArray()
        )
        val ENCRYPTED_PLATFORM_NAME = EncryptedData(
            iv = "platformNameIv".toByteArray(),
            data = PLATFORM_NAME.toByteArray()
        )
    }
}