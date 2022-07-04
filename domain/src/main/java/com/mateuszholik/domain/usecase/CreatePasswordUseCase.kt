package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.factories.PasswordFactory
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface CreatePasswordUseCase : ParameterizedSingleUseCase<Int, String>

internal class CreatePasswordUseCaseImpl(
    private val passwordFactory: PasswordFactory
) : CreatePasswordUseCase {

    override fun invoke(param: Int): Single<String> =
        Single.just(passwordFactory.create(param))
}