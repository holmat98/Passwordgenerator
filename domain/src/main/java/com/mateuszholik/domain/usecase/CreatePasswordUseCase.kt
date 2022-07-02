package com.mateuszholik.domain.usecase

import com.mateuszholik.domain.constants.Constants.EMPTY_STRING
import com.mateuszholik.domain.extensions.getAndRemove
import com.mateuszholik.domain.extensions.getRandom
import com.mateuszholik.domain.models.PasswordCharacterType
import com.mateuszholik.domain.usecase.base.ParameterizedSingleUseCase
import io.reactivex.rxjava3.core.Single

interface CreatePasswordUseCase : ParameterizedSingleUseCase<Int, String>

internal class CreatePasswordUseCaseImpl : CreatePasswordUseCase {

    override fun invoke(param: Int): Single<String> =
        Single.just(createPassword(param))

    private fun createPassword(passwordLength: Int): String {
        val freeIndexes = (0 until passwordLength).toMutableList()
        var currentCharacterType = PasswordCharacterType.values().random()
        val result = MutableList(passwordLength) { EMPTY_STRING }

        while (freeIndexes.isNotEmpty()) {
            val index = freeIndexes.getAndRemove()
            PASSWORD_CHARACTERS[currentCharacterType]?.let {
                result[index] = it.getRandom()
                currentCharacterType = currentCharacterType.next
            }
        }

        return result.joinToString(EMPTY_STRING)
    }

    private companion object {
        val PASSWORD_CHARACTERS = mapOf(
            PasswordCharacterType.LETTERS to "abcdefghijklmnopqrstuvwxyz",
            PasswordCharacterType.CAPITAL_LETTERS to "ABCDEFGHIJKLMNOPQESTUVWXYZ",
            PasswordCharacterType.SPECIAL_CHARACTERS to "!@#$%^&*-_+=?",
            PasswordCharacterType.NUMBERS to "0123456789"
        )
    }
}