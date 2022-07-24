package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.extensions.toPasswordType
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single
import java.time.LocalDateTime

interface GetPasswordsUseCase : UseCase<List<PasswordType>>

internal class GetPasswordsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository
) : GetPasswordsUseCase {

    override fun invoke(): Single<List<PasswordType>> =
        passwordsRepository.getAllPasswords()
            .map { passwords ->
                passwords.map { password ->
                    password.toPasswordType(LocalDateTime.now())
                }
            }
}