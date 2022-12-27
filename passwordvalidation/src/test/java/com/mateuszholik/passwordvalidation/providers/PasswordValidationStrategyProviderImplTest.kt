package com.mateuszholik.passwordvalidation.providers

import org.assertj.core.api.Assertions.assertThat
import com.mateuszholik.passwordvalidation.db.daos.CommonPasswordDao
import com.mateuszholik.passwordvalidation.db.daos.CommonWordDao
import com.mateuszholik.passwordvalidation.models.PasswordValidationType
import com.mateuszholik.passwordvalidation.strategies.*
import com.mateuszholik.passwordvalidation.transformers.StringTransformer
import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordANameUseCase
import com.mateuszholik.passwordvalidation.usecases.GetIsPasswordAPetNameUseCase
import io.mockk.mockk
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

internal class PasswordValidationStrategyProviderImplTest {

    private val commonPasswordDao = mockk<CommonPasswordDao>()
    private val getIsPasswordANameUseCase = mockk<GetIsPasswordANameUseCase>()
    private val getIsPasswordAPetNameUseCase = mockk<GetIsPasswordAPetNameUseCase>()
    private val commonWordDao = mockk<CommonWordDao>()
    private val stringTransformer = mockk<StringTransformer>()

    private val passwordValidationStrategyProvider = PasswordValidationStrategyProviderImpl(
        commonPasswordDao = commonPasswordDao,
        getIsPasswordANameUseCase = getIsPasswordANameUseCase,
        getIsPasswordAPetNameUseCase = getIsPasswordAPetNameUseCase,
        commonWordDao = commonWordDao,
        stringTransformer = stringTransformer
    )

    @TestFactory
    fun checkProvidedValidationStrategies() =
        listOf(
            PasswordValidationType.SMALL_LETTERS to SmallLetterValidationStrategyImpl::class.java,
            PasswordValidationType.UPPERCASE_LETTERS to UppercaseLettersValidationStrategyImpl::class.java,
            PasswordValidationType.SPECIAL_CHARACTERS to SpecialCharacterValidationStrategyImpl::class.java,
            PasswordValidationType.NUMBERS to NumberValidationStrategyImpl::class.java,
            PasswordValidationType.LENGTH to LengthValidationStrategyImpl::class.java,
            PasswordValidationType.COMMON_PASSWORD to CommonPasswordValidationStrategyImpl::class.java,
            PasswordValidationType.COMMON_WORD to CommonWordsValidationStrategyImpl::class.java,
            PasswordValidationType.COMMON_NAME to CommonNameValidationStrategyImpl::class.java,
            PasswordValidationType.ALPHABETICAL_PATTERN to AlphabeticalPatternsValidationStrategyImpl::class.java,
            PasswordValidationType.KEYBOARD_PATTERN to KeyboardPatternsValidationStrategyImpl::class.java
        ).map { (validationType, strategyImplementationType) ->
            dynamicTest("When given validation type is $validationType returned validation strategy implementation is of type $strategyImplementationType") {
                val result = passwordValidationStrategyProvider.provide(validationType)

                assertThat(result).isInstanceOf(strategyImplementationType)
            }
        }
}