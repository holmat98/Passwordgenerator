package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.domain.models.ExportedPassword

internal interface ExportedPasswordToNewPasswordMapper : Mapper<ExportedPassword, NewPassword>

internal class ExportedPasswordToNewPasswordMapperImpl : ExportedPasswordToNewPasswordMapper {

    override fun map(param: ExportedPassword): NewPassword =
        NewPassword(
            platformName = param.platformName,
            password = param.password,
            website = null,
            isExpiring = false,
        )
}
