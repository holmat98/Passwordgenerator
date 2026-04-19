package com.mateuszholik.domainbase.usecases

import com.mateuszholik.domainbase.models.Result

interface ResultUseCase<TOutput> : UseCase {

    suspend operator fun invoke(): Result<TOutput>
}
