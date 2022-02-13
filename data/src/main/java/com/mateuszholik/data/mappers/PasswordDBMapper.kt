package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.repositories.models.Password

internal interface PasswordDBMapper : Mapper<Password, PasswordDB>

internal class PasswordDBMapperImpl : PasswordDBMapper {

    override fun map(param: Password): PasswordDB =
        PasswordDB(
            id = param.id,
            platformName = param.platformName,
            password = param.password,
            expiringDate = param.expiringDate
        )
}

