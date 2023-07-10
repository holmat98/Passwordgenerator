package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface GetPasswordsUseCase : UseCase<List<PasswordInfo>>

internal class GetPasswordsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
) : GetPasswordsUseCase {

    override fun invoke(): Single<List<PasswordInfo>> =
        passwordsRepository.getAllPasswords()
}
