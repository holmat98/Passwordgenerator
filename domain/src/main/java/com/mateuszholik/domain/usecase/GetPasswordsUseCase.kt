package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.mappers.PasswordsListToPasswordsTypeListMapper
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface GetPasswordsUseCase : UseCase<List<PasswordType>>

internal class GetPasswordsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val passwordsListToPasswordsTypeListMapper: PasswordsListToPasswordsTypeListMapper
) : GetPasswordsUseCase {

    override fun invoke(): Single<List<PasswordType>> =
        passwordsRepository.getAllPasswords()
            .map { passwordsListToPasswordsTypeListMapper.map(it) }
}