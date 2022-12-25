package com.mateuszholik.domain.mappers

internal interface ListMapper<TInput, TOutput> {

    fun map(param: List<TInput>): List<TOutput>
}