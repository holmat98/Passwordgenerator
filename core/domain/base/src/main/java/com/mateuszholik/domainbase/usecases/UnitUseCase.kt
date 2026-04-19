package com.mateuszholik.domainbase.usecases

interface UnitUseCase : UseCase {

    suspend operator fun invoke()
}
