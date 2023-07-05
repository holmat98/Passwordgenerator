package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.usecase.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface MigrateDataToTheCorrectStateUseCase : CompletableUseCase

internal class MigrateDataToTheCorrectStateUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
    private val getPasswordScoreUseCase: GetPasswordScoreUseCase,
) : MigrateDataToTheCorrectStateUseCase {

    override fun invoke(): Completable =
        passwordsRepository.getAllPasswords()
            .flatMapObservable { Observable.fromIterable(it) }
            .flatMapCompletable { password ->
                getPasswordScoreUseCase(password.password)
                    .flatMapCompletable { passwordScore ->
                        passwordsRepository.updatePlatformAndPasswordScoreFor(
                            id = password.id,
                            platform = password.platformName,
                            passwordScore = passwordScore
                        )
                    }
            }

}
