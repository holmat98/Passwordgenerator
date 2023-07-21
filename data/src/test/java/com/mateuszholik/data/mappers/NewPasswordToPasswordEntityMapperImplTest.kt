package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.managers.io.SharedPrefKeys.PASSWORD_VALIDITY
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.mappers.NewPasswordToPasswordEntityMapper.Param
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class NewPasswordToPasswordEntityMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every { encrypt(PASSWORD) } returns ENCRYPTED_PASSWORD
    }
    private val sharedPrefManager = mockk<SharedPrefManager> {
        every {
            readLong(PASSWORD_VALIDITY, any())
        } returns PASSWORD_VALIDITY_IN_DAYS
    }
    private val newPasswordToPasswordEntityMapper = NewPasswordToPasswordEntityMapperImpl(
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
    fun `For given param mapper will properly map it to PasswordEntity model`() {
        val result = newPasswordToPasswordEntityMapper.map(TESTED_VALUE)

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
    fun `When isExpiring is equal to false then expirationDate is equal to null and param correctly mapped to PasswordEntity object`() {
        val result = newPasswordToPasswordEntityMapper.map(TESTED_VALUE_EXPIRING)

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
        const val ID = 0L
        const val PASSWORD = "password"
        const val PASSWORD_SCORE = 90
        const val NAME_ID = 1L
        val TESTED_VALUE = Param(
            password = PASSWORD,
            passwordScore = PASSWORD_SCORE,
            isExpiring = true,
            nameId = NAME_ID
        )
        val TESTED_VALUE_EXPIRING = Param(
            password = PASSWORD,
            passwordScore = PASSWORD_SCORE,
            isExpiring = false,
            nameId = NAME_ID
        )
        val ENCRYPTED_PASSWORD = EncryptedData(
            iv = ByteArray(5) { it.toByte() },
            data = PASSWORD.toByteArray()
        )
    }
}
