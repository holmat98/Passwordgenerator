package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType
import java.time.LocalDateTime

internal interface PasswordToPasswordTypeMapper : Mapper<Password, PasswordType>

internal class PasswordToPasswordTypeMapperImpl : PasswordToPasswordTypeMapper {

    override fun map(param: Password): PasswordType {
        val now = LocalDateTime.now()

        return when {
            now.isBefore(param.expiringDate.minusDays(EXPIRING_PASSWORD_TIME_IN_DAYS)) ->
                PasswordType.ValidPassword(param)
            now.isAfter(param.expiringDate) -> PasswordType.OutdatedPassword(param)
            else -> PasswordType.ExpiringPassword(param)
        }
    }

    private companion object {
        const val EXPIRING_PASSWORD_TIME_IN_DAYS = 7L
    }

}