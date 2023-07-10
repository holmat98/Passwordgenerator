package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.models.PasswordInfo
import com.mateuszholik.domain.models.PasswordType

internal interface PasswordToPasswordTypeMapper : Mapper<PasswordInfo, PasswordType>

internal class PasswordToPasswordTypeMapperImpl : PasswordToPasswordTypeMapper {

    override fun map(param: PasswordInfo): PasswordType =
        PasswordType.ValidPassword(param.passwordInfo, param.passwordScore)
}
