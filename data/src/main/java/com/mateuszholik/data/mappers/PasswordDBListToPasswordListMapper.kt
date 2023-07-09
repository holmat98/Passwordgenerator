package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.entities.OldPasswordEntity
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordDBListToPasswordListMapper : ListMapper<OldPasswordEntity, Password>

internal class PasswordDBListToPasswordListMapperImpl(
    private val passwordDBToPasswordMapper: PasswordDBToPasswordMapper
) : PasswordDBListToPasswordListMapper {

    override fun map(param: List<OldPasswordEntity>): List<Password> =
        param.map { passwordDBToPasswordMapper.map(it) }
}
