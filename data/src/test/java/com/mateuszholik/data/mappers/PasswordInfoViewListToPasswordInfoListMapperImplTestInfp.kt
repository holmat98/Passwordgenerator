package com.mateuszholik.data.mappers

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

class PasswordInfoViewListToPasswordInfoListMapperImplTestInfp {

    private val passwordInfoViewToPasswordInfoMapper = mockk<PasswordInfoViewToPasswordInfoMapper> {
        every { map(PASSWORD_INFO_VIEW_1) } returns MAPPED_PASSWORD_INFO_1
        every { map(PASSWORD_INFO_VIEW_2) } returns MAPPED_PASSWORD_INFO_2
    }

    private val passwordListMapper = PasswordInfoViewListToPasswordInfoListMapperImpl(passwordInfoViewToPasswordInfoMapper)

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
    fun `PasswordListMapper maps correctly list of PasswordDB objects to list of Password objects`() {
        val result = passwordListMapper.map(listOf(PASSWORD_INFO_VIEW_1, PASSWORD_INFO_VIEW_2))

        assertThat(result).isEqualTo(listOf(MAPPED_PASSWORD_INFO_1, MAPPED_PASSWORD_INFO_2))
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PLATFORM_NAME_2 = "platform2"
        val TODAY: LocalDateTime = LocalDateTime.of(2022, 6, 13, 12, 0 , 0)
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
            platformName = ByteArray(10),
            platformNameIv = ByteArray(11),
            passwordScore = 90,
            expirationDate = null
        )
        val MAPPED_PASSWORD_INFO_1 = PasswordInfo(
            id = 1,
            platformName = PLATFORM_NAME,
            passwordScore = 50,
            passwordValidity = PasswordValidity.Expiring(EXPIRATION_DATE)
        )
        val MAPPED_PASSWORD_INFO_2 = PasswordInfo(
            id = 2,
            platformName = PLATFORM_NAME_2,
            passwordScore = 50,
            passwordValidity = PasswordValidity.NeverExpires
        )
    }
}
