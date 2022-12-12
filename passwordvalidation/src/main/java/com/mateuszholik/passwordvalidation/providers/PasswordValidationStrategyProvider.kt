package com.mateuszholik.passwordvalidation.providers

import com.mateuszholik.passwordvalidation.db.daos.CommonNameDao
import com.mateuszholik.passwordvalidation.db.daos.CommonPasswordDao
import com.mateuszholik.passwordvalidation.db.daos.CommonPetsNameDao
import com.mateuszholik.passwordvalidation.db.daos.CommonWordDao
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import com.mateuszholik.passwordvalidation.models.PasswordValidationType.*
import com.mateuszholik.passwordvalidation.strategies.*
import com.mateuszholik.passwordvalidation.strategies.NumberValidationStrategyImpl
import com.mateuszholik.passwordvalidation.strategies.PasswordValidationStrategy
import com.mateuszholik.passwordvalidation.strategies.SpecialCharacterValidationStrategyImpl
import com.mateuszholik.passwordvalidation.transformers.StringTransformer

internal interface PasswordValidationStrategyProvider {

    fun provide(passwordValidationType: PasswordValidationType): PasswordValidationStrategy
}

internal class PasswordValidationStrategyProviderImpl(
    private val commonPasswordDao: CommonPasswordDao,
    private val commonNameDao: CommonNameDao,
    private val commonPetsNameDao: CommonPetsNameDao,
    private val commonWordDao: CommonWordDao,
    private val stringTransformer: StringTransformer
) : PasswordValidationStrategyProvider {

    override fun provide(passwordValidationType: PasswordValidationType): PasswordValidationStrategy =
        when (passwordValidationType) {
            SMALL_LETTERS -> SmallLetterValidationStrategyImpl()
            UPPERCASE_LETTERS -> UppercaseLettersValidationStrategyImpl()
            SPECIAL_CHARACTERS -> SpecialCharacterValidationStrategyImpl()
            NUMBERS -> NumberValidationStrategyImpl()
            LENGTH -> LengthValidationStrategyImpl()
            COMMON_PASSWORD -> CommonPasswordValidationStrategyImpl(commonPasswordDao)
            COMMON_WORD -> CommonWordsValidationStrategyImpl(commonWordDao, stringTransformer)
            COMMON_NAME -> CommonNameValidationStrategyImpl(commonNameDao, commonPetsNameDao)
            ALPHABETICAL_PATTERN -> AlphabeticalPatternsValidationStrategyImpl()
            KEYBOARD_PATTERN -> KeyboardPatternsValidationStrategyImpl()
        }
}