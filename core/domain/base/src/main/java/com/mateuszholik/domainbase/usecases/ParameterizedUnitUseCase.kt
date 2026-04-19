package com.mateuszholik.domainbase.usecases

interface ParameterizedUnitUseCase<TInput> : UseCase {

    suspend operator fun invoke(param: TInput)
}
