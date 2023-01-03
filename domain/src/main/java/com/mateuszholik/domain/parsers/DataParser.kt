package com.mateuszholik.domain.parsers

internal interface DataParser<T> {

    fun parseToString(data: T): String

    fun parseFromString(data: String): T
}