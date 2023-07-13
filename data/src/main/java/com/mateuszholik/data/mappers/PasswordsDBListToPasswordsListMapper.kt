package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordsDBListToPasswordsListMapper : ListMapper<PasswordDB, Password>

internal class PasswordsDBListToPasswordsListMapperImpl(
    private val passwordDBToPasswordMapper: PasswordDBToPasswordMapper,
) : PasswordsDBListToPasswordsListMapper {

    override fun map(param: List<PasswordDB>): List<Password> =
        param.map { passwordDBToPasswordMapper.map(it) }
}
