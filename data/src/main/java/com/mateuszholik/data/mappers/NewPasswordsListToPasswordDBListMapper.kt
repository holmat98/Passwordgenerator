package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.OldPasswordEntity
import com.mateuszholik.data.repositories.models.NewPassword

internal interface NewPasswordsListToPasswordDBListMapper : ListMapper<NewPassword, OldPasswordEntity>

internal class NewPasswordsListToPasswordDBListMapperImpl(
    private val newPasswordToPasswordDBMapper: NewPasswordToPasswordDBMapper
) : NewPasswordsListToPasswordDBListMapper {

    override fun map(param: List<NewPassword>): List<OldPasswordEntity> =
        param.map { newPasswordToPasswordDBMapper.map(it) }
}
