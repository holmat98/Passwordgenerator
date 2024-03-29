package com.mateuszholik.domain.usecase.base

import io.reactivex.rxjava3.core.Maybe

interface ParameterizedMaybeUseCase<TInput, TOutput: Any> {

    operator fun invoke(param: TInput): Maybe<TOutput>
}