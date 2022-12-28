package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.ExportedPassword

internal interface PasswordToExportedPasswordMapper : Mapper<Password, ExportedPassword>

internal class PasswordToExportedPasswordMapperImpl : PasswordToExportedPasswordMapper {

    override fun map(param: Password): ExportedPassword =
        ExportedPassword(
            platformName = param.platformName,
            password = param.password
        )
}