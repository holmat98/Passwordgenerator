package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.models.PasswordInfo
import com.mateuszholik.domain.models.PasswordType
import java.time.LocalDateTime

internal interface PasswordToPasswordTypeMapper : Mapper<PasswordInfo, PasswordType>

internal class PasswordToPasswordTypeMapperImpl : PasswordToPasswordTypeMapper {

    override fun map(param: PasswordInfo): PasswordType {
        val now = LocalDateTime.now()

        return when {
            now.isBefore(param.password.expiringDate.minusDays(EXPIRING_PASSWORD_TIME_IN_DAYS)) ->
                PasswordType.ValidPassword(param.password, param.passwordScore)
            now.isAfter(param.password.expiringDate) ->
                PasswordType.OutdatedPassword(param.password, param.passwordScore)
            else -> PasswordType.ExpiringPassword(param.password, param.passwordScore)
        }
    }

    private companion object {
        const val EXPIRING_PASSWORD_TIME_IN_DAYS = 7L
    }

}
