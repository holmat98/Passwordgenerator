package com.mateuszholik.domain.mappers

import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType

internal interface PasswordsListToPasswordsTypeListMapper : ListMapper<Password, PasswordType>

internal class PasswordsListToPasswordsTypeListMapperImpl(
    private val passwordToPasswordTypeMapper: PasswordToPasswordTypeMapper
) : PasswordsListToPasswordsTypeListMapper {

    override fun map(param: List<Password>): List<PasswordType> =
        param.map { passwordToPasswordTypeMapper.map(it) }
}