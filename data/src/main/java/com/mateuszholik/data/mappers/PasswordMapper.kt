package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordMapper : Mapper<PasswordDB, Password>

internal class PasswordMapperImpl : PasswordMapper {

    override fun map(param: PasswordDB): Password =
        Password(
            id = param.id,
            platformName = param.platformName,
            password = param.password,
            expiringDate = param.expiringDate
        )
}