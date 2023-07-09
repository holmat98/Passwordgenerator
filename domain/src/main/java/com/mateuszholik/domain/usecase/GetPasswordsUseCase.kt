package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.domain.mappers.PasswordToPasswordTypeMapper
import com.mateuszholik.domain.models.PasswordInfo
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface GetPasswordsUseCase : UseCase<List<PasswordType>>

internal class GetPasswordsUseCaseImpl(
    private val oldPasswordsRepository: OldPasswordsRepository,
    private val passwordToPasswordTypeMapper: PasswordToPasswordTypeMapper,
    private val getPasswordValidationResultUseCase: GetPasswordValidationResultUseCase,
) : GetPasswordsUseCase {

    override fun invoke(): Single<List<PasswordType>> =
        oldPasswordsRepository.getAllPasswords()
            .flatMapObservable {
                Observable.fromIterable(it)
            }
            .flatMap { password ->
                getPasswordValidationResultUseCase(password.password).toObservable()
                    .map { validationResult ->
                        val score = validationResult.sumOf { it.score }
                        val maxScore = validationResult.sumOf { it.maxScore }

                        val scorePct = ((score.toFloat() / maxScore.toFloat()) * 100).toInt()

                        passwordToPasswordTypeMapper.map(PasswordInfo(password, scorePct))
                    }
            }
            .toList()
}
