package com.mateuszholik.passwordgenerator.mappers

import android.content.Context
import androidx.annotation.StringRes
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordvalidation.models.PasswordValidationType

interface PasswordValidationTypeToTextMapper {

    fun map(validationType: PasswordValidationType): String
}

class PasswordValidationTypeToTextMapperImpl(
    private val context: Context
) : PasswordValidationTypeToTextMapper {

    override fun map(validationType: PasswordValidationType): String =
        when (validationType) {
            PasswordValidationType.SMALL_LETTERS -> getString(R.string.password_validation_letter)
            PasswordValidationType.UPPERCASE_LETTERS -> getString(R.string.password_validation_uppercase)
            PasswordValidationType.LENGTH -> getString(R.string.password_validation_length)
            PasswordValidationType.SPECIAL_CHARACTERS -> getString(R.string.password_validation_special_character)
            PasswordValidationType.NUMBERS -> getString(R.string.password_validation_number)
            PasswordValidationType.COMMON_PASSWORD -> getString(R.string.password_validation_common_password)
            PasswordValidationType.COMMON_NAME -> getString(R.string.password_validation_name)
            PasswordValidationType.COMMON_WORD -> getString(R.string.password_validation_word)
        }

    private fun getString(@StringRes stringRes: Int) = context.getString(stringRes)
}