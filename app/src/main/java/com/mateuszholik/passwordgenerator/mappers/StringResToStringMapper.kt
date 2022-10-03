package com.mateuszholik.passwordgenerator.mappers

import android.content.Context

interface StringResToStringMapper : ObjectToTextMapper<Int>

class StringResToStringMapperImpl(
    private val context: Context
) : StringResToStringMapper {

    override fun map(param: Int): String = context.getString(param)
}