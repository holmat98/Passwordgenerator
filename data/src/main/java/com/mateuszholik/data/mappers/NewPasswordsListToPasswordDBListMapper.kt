package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordEntity
import com.mateuszholik.data.repositories.models.NewPassword

internal interface NewPasswordsListToPasswordDBListMapper : ListMapper<NewPassword, PasswordEntity>

internal class NewPasswordsListToPasswordDBListMapperImpl(
    private val newPasswordToPasswordDBMapper: NewPasswordToPasswordDBMapper
) : NewPasswordsListToPasswordDBListMapper {

    override fun map(param: List<NewPassword>): List<PasswordEntity> =
        param.map { newPasswordToPasswordDBMapper.map(it) }
}
