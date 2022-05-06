package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.Resource
import com.mateuszholik.domain.models.ErrorState
import com.mateuszholik.domain.models.State
import com.mateuszholik.domain.usecase.base.UseCase
import io.reactivex.rxjava3.core.Single

interface GetPasswordsUseCase : UseCase<State<List<Password>>>

internal class GetPasswordsUseCaseImpl(
    private val passwordsRepository: PasswordsRepository
) : GetPasswordsUseCase {

    override fun invoke(): Single<State<List<Password>>> =
        passwordsRepository.getAllPasswords().map {
            when (it) {
                is Resource.Success -> State.Success(it.data)
                is Resource.EmptyBody -> State.EmptyBody()
                else -> State.Error(ErrorState.UNKNOWN_ERROR)
            }
        }
            .doOnError {
                State.Error<List<Password>>(ErrorState.GET_PASSWORDS_ERROR)
            }
}