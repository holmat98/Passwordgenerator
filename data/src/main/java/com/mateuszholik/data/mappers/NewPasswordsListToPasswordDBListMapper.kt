package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.NewPassword

internal interface NewPasswordsListToPasswordDBListMapper : ListMapper<NewPassword, PasswordDB>

internal class NewPasswordsListToPasswordDBListMapperImpl(
    private val newPasswordToPasswordDBMapper: NewPasswordToPasswordDBMapper
) : NewPasswordsListToPasswordDBListMapper {

    override fun map(param: List<NewPassword>): List<PasswordDB> =
        param.map { newPasswordToPasswordDBMapper.map(it) }
}