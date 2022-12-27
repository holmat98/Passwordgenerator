package com.mateuszholik.data.mappers

import io.reactivex.rxjava3.core.Single

interface SingleMapper<T, R> {

    fun map(param: T): Single<R>
}