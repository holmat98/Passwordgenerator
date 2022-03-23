package com.mateuszholik.domain.usecase.base

import io.reactivex.rxjava3.core.Completable

interface CompletableUseCase<TInput> {

    operator fun invoke(param: TInput): Completable
}