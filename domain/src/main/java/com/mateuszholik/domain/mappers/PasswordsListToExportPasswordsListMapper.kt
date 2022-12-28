package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.ExportedPassword

internal interface PasswordsListToExportPasswordsListMapper : ListMapper<Password, ExportedPassword>

internal class PasswordsListToExportPasswordsListMapperImpl(
    private val passwordToExportedPasswordMapper: PasswordToExportedPasswordMapper
) : PasswordsListToExportPasswordsListMapper {

    override fun map(param: List<Password>): List<ExportedPassword> =
        param.map { passwordToExportedPasswordMapper.map(it) }
}