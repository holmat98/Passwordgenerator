package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.views.PasswordInfoView
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.data.repositories.models.PasswordValidity
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PasswordInfoViewToPasswordInfoMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every {
            decrypt(
                EncryptedData(
                    iv = PASSWORD_INFO_VIEW_1.platformNameIv,
                    data = PASSWORD_INFO_VIEW_1.platformName
                )
            )
        } returns PLATFORM_NAME

        every {
            decrypt(
                EncryptedData(
                    iv = PASSWORD_INFO_VIEW_2.platformNameIv,
                    data = PASSWORD_INFO_VIEW_2.platformName
                )
            )
        } returns PLATFORM_NAME_2
    }

    private val passwordMapper = PasswordInfoViewToPasswordInfoMapperImpl(encryptionManager)

    @BeforeEach
    fun setUp() {
        mockkStatic(LocalDateTime::class)
        every {
            LocalDateTime.now()
        } returns TODAY
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(LocalDateTime::class)
    }

    @Test
    fun `PasswordInfoView is correctly mapped to PasswordInfo object`() {
        val result = passwordMapper.map(PASSWORD_INFO_VIEW_1)

        assertThat(result).isEqualTo(MAPPED_PASSWORD_INFO_1)
    }

    @Test
    fun `PasswordInfoView with expirationDate equal to null is correctly mapped to PasswordInfo object`() {
        val result = passwordMapper.map(PASSWORD_INFO_VIEW_2)

        assertThat(result).isEqualTo(MAPPED_PASSWORD_INFO_2)
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PLATFORM_NAME_2 = "platform2"
        val TODAY: LocalDateTime = LocalDateTime.of(2022, 6, 13, 12, 0, 0)
        val EXPIRATION_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_INFO_VIEW_1 = PasswordInfoView(
            id = 1,
            platformName = ByteArray(10),
            platformNameIv = ByteArray(11),
            passwordScore = 50,
            expirationDate = EXPIRATION_DATE
        )
        val PASSWORD_INFO_VIEW_2 = PasswordInfoView(
            id = 2,
            platformName = ByteArray(12),
            platformNameIv = ByteArray (13),
            passwordScore = 90,
            expirationDate = null
        )
        val MAPPED_PASSWORD_INFO_1 = PasswordInfo(
            id = 1,
            platformName = PLATFORM_NAME,
            passwordScore = 50,
            passwordValidity = PasswordValidity.Expired(EXPIRATION_DATE)
        )
        val MAPPED_PASSWORD_INFO_2 = PasswordInfo(
            id = 2,
            platformName = PLATFORM_NAME_2,
            passwordScore = 90,
            passwordValidity = PasswordValidity.NeverExpires
        )
    }
}
