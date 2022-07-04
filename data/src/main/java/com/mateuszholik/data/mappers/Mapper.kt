package com.mateuszholik.data.mappers

internal interface Mapper<T, R> {

    fun map(param: T): R
}