package com.mateuszholik.data.mappers

internal interface ListMapper<T, R> {

    fun map(param: List<T>): List<R>
}