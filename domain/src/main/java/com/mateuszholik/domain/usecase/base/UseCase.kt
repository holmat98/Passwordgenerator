package com.mateuszholik.domain.usecase.base

import io.reactivex.rxjava3.core.Single

interface UseCase<TOutput: Any> {

    operator fun invoke(): Single<TOutput>
}