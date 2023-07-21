package com.mateuszholik.domain.usecase.base

import io.reactivex.rxjava3.core.Completable

interface CompletableUseCase {

    operator fun invoke(): Completable
}
interface CompletableParameterizedUseCase<TInput> {

    operator fun invoke(param: TInput): Completable
}
