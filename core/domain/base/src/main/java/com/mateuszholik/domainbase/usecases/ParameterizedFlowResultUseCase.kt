package com.mateuszholik.domainbase.usecases

import com.mateuszholik.domainbase.models.Result

interface ParameterizedFlowResultUseCase<TInput, TOutput> : UseCase {

    operator fun invoke(param: TInput): Result<TOutput>
}
