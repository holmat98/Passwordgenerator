package com.mateuszholik.domainbase.usecases

import com.mateuszholik.domainbase.models.Result

interface ParameterizedResultUseCase<TInput, TOutput> : UseCase {

    suspend operator fun invoke(param: TInput): Result<TOutput>
}
