package com.mateuszholik.domain.mappers

internal interface Mapper<TInput, TOutput> {

    fun map(param: TInput): TOutput
}