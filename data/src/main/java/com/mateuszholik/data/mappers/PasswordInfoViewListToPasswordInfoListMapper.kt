package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.views.PasswordInfoView
import com.mateuszholik.data.repositories.models.PasswordInfo

internal interface PasswordInfoViewListToPasswordInfoListMapper : ListMapper<PasswordInfoView, PasswordInfo>

internal class PasswordInfoViewListToPasswordInfoListMapperImpl(
    private val passwordInfoViewToPasswordInfoMapper: PasswordInfoViewToPasswordInfoMapper
) : PasswordInfoViewListToPasswordInfoListMapper {

    override fun map(param: List<PasswordInfoView>): List<PasswordInfo> =
        param.map { passwordInfoViewToPasswordInfoMapper.map(it) }
}
