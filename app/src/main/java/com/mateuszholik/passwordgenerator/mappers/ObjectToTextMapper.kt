package com.mateuszholik.passwordgenerator.mappers

interface ObjectToTextMapper<T> {

    fun map(param: T): String
}