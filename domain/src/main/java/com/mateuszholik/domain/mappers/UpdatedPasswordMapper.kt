package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.models.UpdatedPassword
import com.mateuszholik.data.repositories.models.UpdatedPassword as DataUpdatedPassword

internal interface UpdatedPasswordMapper : Mapper<UpdatedPassword, DataUpdatedPassword>

internal class UpdatedPasswordMapperImpl : UpdatedPasswordMapper {

    override fun map(param: UpdatedPassword): DataUpdatedPassword =
        DataUpdatedPassword(
            id = param.id,
            password = param.password,
            platformName = param.platformName
        )
}