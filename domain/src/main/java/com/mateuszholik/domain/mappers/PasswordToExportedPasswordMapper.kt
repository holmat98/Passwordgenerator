package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.domain.models.ExportedPassword

internal interface PasswordToExportedPasswordMapper : Mapper<PasswordInfo, ExportedPassword>

internal class PasswordToExportedPasswordMapperImpl : PasswordToExportedPasswordMapper {

    override fun map(param: PasswordInfo): ExportedPassword =
        ExportedPassword(
            platformName = param.platformName,
            password = ""
        )
}
