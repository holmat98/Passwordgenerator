package com.mateuszholik.domain.usecase.base

import io.reactivex.rxjava3.core.Single

interface ParameterizedSingleUseCase<TInput, TOutput: Any> {

    operator fun invoke(param: TInput): Single<TOutput>
}