package com.mateuszholik.data.mappers

import com.mateuszholik.data.cryptography.EncryptionManager
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordListMapper : ListMapper<PasswordDB, Password>

internal class PasswordListMapperImpl(
    private val passwordMapper: PasswordMapper
) : PasswordListMapper {

    override fun map(param: List<PasswordDB>): List<Password> =
        param.map { passwordMapper.map(it) }
}