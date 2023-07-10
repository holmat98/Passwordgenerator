package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.entities.OldPasswordEntity
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.data.repositories.models.PasswordValidity
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PasswordInfoViewListToPasswordInfoListMapperImplTestInfp {

    private val passwordInfoViewToPasswordInfoMapper = mockk<PasswordInfoViewToPasswordInfoMapper> {
        every { map(PASSWORD_DB_1) } returns MAPPED_PASSWORD_1Infp
        every { map(PASSWORD_DB_2) } returns MAPPED_PASSWORD_2Infp
    }

    private val passwordListMapper = PasswordInfoViewListToPasswordInfoListMapperImpl(passwordInfoViewToPasswordInfoMapper)

    @Test
    fun `PasswordListMapper maps correctly list of PasswordDB objects to list of Password objects`() {
        val result = passwordListMapper.map(listOf(PASSWORD_DB_1, PASSWORD_DB_2))

        assertThat(result).isEqualTo(listOf(MAPPED_PASSWORD_1Infp, MAPPED_PASSWORD_2Infp))
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password1234"
        const val PLATFORM_NAME_2 = "platform2"
        const val PASSWORD_2 = "password12345"
        val EXPIRING_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_DB_1 = OldPasswordEntity(
            id = 1,
            platformName = ByteArray(10),
            platformIV = ByteArray(11),
            password = ByteArray(12),
            passwordIV = ByteArray(13),
            expirationDate = EXPIRING_DATE
        )
        val PASSWORD_DB_2 = OldPasswordEntity(
            id = 2,
            platformName = ByteArray(10),
            platformIV = ByteArray(11),
            password = ByteArray(12),
            passwordIV = ByteArray(13),
            expirationDate = EXPIRING_DATE
        )
        val MAPPED_PASSWORD_INFO_1 = PasswordInfo(
            id = 1,
            platformName = PLATFORM_NAME,
            passwordScore = 50,
            passwordValidity = PasswordValidity.EXPIRING
        )
        val MAPPED_PASSWORD_INFO_2 = PasswordInfo(
            id = 2,
            platformName = PLATFORM_NAME_2,
            passwordScore = 50,
            passwordValidity = PasswordValidity.EXPIRING
        )
    }
}
