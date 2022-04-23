package com.mateuszholik.domain.validators

internal interface BaseValidator<T> {

    fun validate(param: T): Boolean
}