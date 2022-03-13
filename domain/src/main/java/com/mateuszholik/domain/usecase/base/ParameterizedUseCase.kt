package com.mateuszholik.domain.usecase.base

import io.reactivex.rxjava3.core.Single

interface ParameterizedUseCase<TInput, TOutput> {

    operator fun invoke(param: TInput): Single<TOutput>
}