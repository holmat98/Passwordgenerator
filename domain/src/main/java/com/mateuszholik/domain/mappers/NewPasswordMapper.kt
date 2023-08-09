package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.mappers.NewPasswordMapper.Param
import com.mateuszholik.data.repositories.models.NewPassword as DataNewPassword

internal interface NewPasswordMapper : Mapper<Param, DataNewPassword> {

    data class Param(
        val platformName: String,
        val password: String,
        val website: String?,
        val isExpiring: Boolean,
        val passwordScore: Int,
        val packageName: String?,
    )
}

internal class NewPasswordMapperImpl : NewPasswordMapper {

    override fun map(param: Param): DataNewPassword =
        DataNewPassword(
            platformName = param.platformName,
            password = param.password,
            website = param.website,
            isExpiring = param.isExpiring,
            passwordScore = param.passwordScore,
            packageName = param.packageName
        )
}
