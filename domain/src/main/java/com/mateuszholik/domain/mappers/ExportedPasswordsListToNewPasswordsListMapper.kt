package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.domain.models.ExportedPassword

internal interface ExportedPasswordsListToNewPasswordsListMapper :
    ListMapper<ExportedPassword, NewPassword>

internal class ExportedPasswordsListToNewPasswordsListMapperImpl(
    private val exportedPasswordToNewPasswordMapper: ExportedPasswordToNewPasswordMapper
) : ExportedPasswordsListToNewPasswordsListMapper {

    override fun map(param: List<ExportedPassword>): List<NewPassword> =
        param.map {
            exportedPasswordToNewPasswordMapper.map(it)
        }
}