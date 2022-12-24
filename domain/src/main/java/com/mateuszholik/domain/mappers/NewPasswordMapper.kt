package com.mateuszholik.domain.mappers

import com.mateuszholik.domain.models.NewPassword
import com.mateuszholik.data.repositories.models.NewPassword as DataNewPassword

internal interface NewPasswordMapper : Mapper<NewPassword, DataNewPassword>

internal class NewPasswordMapperImpl : NewPasswordMapper {

    override fun map(param: NewPassword): DataNewPassword =
        DataNewPassword(
            platformName = param.platformName,
            password = param.password
        )
}