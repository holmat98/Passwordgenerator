package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordEntity
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordDBListToPasswordListMapper : ListMapper<PasswordEntity, Password>

internal class PasswordDBListToPasswordListMapperImpl(
    private val passwordDBToPasswordMapper: PasswordDBToPasswordMapper
) : PasswordDBListToPasswordListMapper {

    override fun map(param: List<PasswordEntity>): List<Password> =
        param.map { passwordDBToPasswordMapper.map(it) }
}
