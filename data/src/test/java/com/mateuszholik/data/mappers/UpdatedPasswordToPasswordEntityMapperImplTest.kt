package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.managers.io.SharedPrefKeys
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordEntityMapper.Param
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class UpdatedPasswordToPasswordEntityMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every {
            encrypt(PASSWORD)
        } returns ENCRYPTED_PASSWORD
    }
    private val sharedPrefManager = mockk<SharedPrefManager> {
        every {
            readLong(SharedPrefKeys.PASSWORD_VALIDITY, any())
        } returns PASSWORD_VALIDITY_IN_DAYS
    }
    private val updatedPasswordToPasswordDBMapper = UpdatedPasswordToPasswordEntityMapperImpl(
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
    fun `For given param object mapper will properly map it to PasswordEntity model`() {
        val result = updatedPasswordToPasswordDBMapper.map(TESTED_VALUE)

        assertThat(result).isEqualTo(
            PasswordEntity(
                id = ID,
                nameId = NAME_ID,
                password = ENCRYPTED_PASSWORD.data,
                passwordIV = ENCRYPTED_PASSWORD.iv,
                passwordScore = PASSWORD_SCORE,
                expirationDate = TODAY_DATE.plusDays(PASSWORD_VALIDITY_IN_DAYS)
            )
        )
    }

    @Test
    fun `For given param object with isExpiring equal to false mapper will properly map it to PasswordEntity model`() {
        val result = updatedPasswordToPasswordDBMapper.map(TESTED_VALUE_NOT_EXPIRING)

        assertThat(result).isEqualTo(
            PasswordEntity(
                id = ID,
                nameId = NAME_ID,
                password = ENCRYPTED_PASSWORD.data,
                passwordIV = ENCRYPTED_PASSWORD.iv,
                passwordScore = PASSWORD_SCORE,
                expirationDate = null
            )
        )
    }

    private companion object {
        val TODAY_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        const val PASSWORD_VALIDITY_IN_DAYS = 30L
        const val ID = 10L
        const val NAME_ID = 1L
        const val PASSWORD = "password"
        const val PASSWORD_SCORE = 50
        val TESTED_VALUE = Param(
            id = ID,
            nameId = NAME_ID,
            password = PASSWORD,
            passwordScore = PASSWORD_SCORE,
            isExpiring = true
        )
        val TESTED_VALUE_NOT_EXPIRING = Param(
            id = ID,
            nameId = NAME_ID,
            password = PASSWORD,
            passwordScore = PASSWORD_SCORE,
            isExpiring = false
        )
        val ENCRYPTED_PASSWORD = EncryptedData(
            iv = ByteArray(5) { it.toByte() },
            data = PASSWORD.toByteArray()
        )
    }
}
