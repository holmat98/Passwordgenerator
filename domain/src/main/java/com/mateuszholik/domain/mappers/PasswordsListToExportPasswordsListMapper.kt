package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.domain.models.ExportedPassword

internal interface PasswordsListToExportPasswordsListMapper : ListMapper<PasswordInfo, ExportedPassword>

internal class PasswordsListToExportPasswordsListMapperImpl(
    private val passwordToExportedPasswordMapper: PasswordToExportedPasswordMapper
) : PasswordsListToExportPasswordsListMapper {

    override fun map(param: List<PasswordInfo>): List<ExportedPassword> =
        param.map { passwordToExportedPasswordMapper.map(it) }
}
