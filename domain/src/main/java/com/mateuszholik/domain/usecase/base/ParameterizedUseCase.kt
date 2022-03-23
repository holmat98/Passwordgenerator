package com.mateuszholik.domain.usecase.base

import com.mateuszholik.domain.models.State
import io.reactivex.rxjava3.core.Single

interface ParameterizedUseCase<TInput, TOutput> {

    operator fun invoke(param: TInput): Single<State<TOutput>>
}