package com.mateuszholik.domainbase.usecases

import com.mateuszholik.domainbase.models.Result

interface FlowResultUseCase<TOutput> : UseCase {

    operator fun invoke(): Result<TOutput>
}
