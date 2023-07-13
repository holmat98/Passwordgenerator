package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.mappers.UpdatedPasswordMapper.Param
import com.mateuszholik.data.repositories.models.UpdatedPassword as DataUpdatedPassword

internal interface UpdatedPasswordMapper : Mapper<Param, DataUpdatedPassword> {

    data class Param(
        val id: Long,
        val password: String,
        val platformName: String,
        val website: String?,
        val isExpiring: Boolean,
        val passwordScore: Int,
    )
}

internal class UpdatedPasswordMapperImpl : UpdatedPasswordMapper {

    override fun map(param: Param): DataUpdatedPassword =
        DataUpdatedPassword(
            id = param.id,
            password = param.password,
            platformName = param.platformName,
            website = param.website,
            isExpiring = param.isExpiring,
            passwordScore = param.passwordScore
        )
}
