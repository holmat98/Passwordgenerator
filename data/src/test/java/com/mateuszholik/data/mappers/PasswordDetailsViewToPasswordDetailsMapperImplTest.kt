package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.views.PasswordDetailsView
import com.mateuszholik.data.repositories.models.PasswordDetails
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

internal class PasswordDetailsViewToPasswordDetailsMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager> {
        every {
            decrypt(
                EncryptedData(
                    iv = IV,
                    data = ENCRYPTED_PASSWORD
                )
            )
        } returns PASSWORD
        every {
            decrypt(
                EncryptedData(
                    iv = IV,
                    data = ENCRYPTED_PLATFORM_NAME
                )
            )
        } returns PLATFORM_NAME
        every {
            decrypt(
                EncryptedData(
                    iv = IV,
                    data = ENCRYPTED_WEBSITE
                )
            )
        } returns WEBSITE
    }

    private val passwordDetailsViewToPasswordDetailsMapper =
        PasswordDetailsViewToPasswordDetailsMapperImpl(
            encryptionManager = encryptionManager
        )

    @BeforeEach
    fun setUp() {
        mockkStatic(LocalDateTime.now()::class)
        every { LocalDateTime.now() } returns TODAY
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(LocalDateTime.now()::class)
    }

    @Test
    fun `PasswordDetailsView is correctly mapped to the PasswordDetails object`() {
        val result = passwordDetailsViewToPasswordDetailsMapper.map(PASSWORD_DETAILS_VIEW)

        assertThat(result).isEqualTo(PASSWORD_DETAILS)
    }

    @Test
    fun `PasswordDetailsView with website and expirationDate equal to null is correctly mapped to the PasswordDetails object`() {
        val result = passwordDetailsViewToPasswordDetailsMapper.map(PASSWORD_DETAILS_VIEW_2)

        assertThat(result).isEqualTo(PASSWORD_DETAILS_2)
    }

    private companion object {
        const val ID = 1L
        const val PLATFORM_NAME = "platformName"
        const val PASSWORD = "password"
        const val WEBSITE = "website"
        const val PASSWORD_SCORE = 50
        val TODAY: LocalDateTime = LocalDateTime.of(2023, 7, 14, 12, 0, 0)
        val ENCRYPTED_PLATFORM_NAME = PLATFORM_NAME.toByteArray()
        val ENCRYPTED_PASSWORD = PASSWORD.toByteArray()
        val ENCRYPTED_WEBSITE = WEBSITE.toByteArray()
        val IV = ByteArray(5) { it.toByte() }
        val PASSWORD_DETAILS_VIEW = PasswordDetailsView(
            id = ID,
            platformName = ENCRYPTED_PLATFORM_NAME,
            platformNameIv = IV,
            password = ENCRYPTED_PASSWORD,
            passwordIv = IV,
            website = ENCRYPTED_WEBSITE,
            websiteIv = IV,
            passwordScore = PASSWORD_SCORE,
            expirationDate = TODAY.plusDays(10)
        )
        val PASSWORD_DETAILS_VIEW_2 = PasswordDetailsView(
            id = ID,
            platformName = ENCRYPTED_PLATFORM_NAME,
            platformNameIv = IV,
            password = ENCRYPTED_PASSWORD,
            passwordIv = IV,
            website = null,
            websiteIv = null,
            passwordScore = PASSWORD_SCORE,
            expirationDate = null
        )
        val PASSWORD_DETAILS = PasswordDetails(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = WEBSITE,
            passwordScore = PASSWORD_SCORE,
            passwordValidity = PasswordValidity.Valid(TODAY.plusDays(10))
        )
        val PASSWORD_DETAILS_2 = PasswordDetails(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            website = null,
            passwordScore = PASSWORD_SCORE,
            passwordValidity = PasswordValidity.NeverExpires
        )
    }
}
