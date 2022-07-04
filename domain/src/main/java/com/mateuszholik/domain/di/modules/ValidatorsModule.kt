package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.validators.*
import com.mateuszholik.domain.validators.ContainsLetterValidator
import com.mateuszholik.domain.validators.ContainsLetterValidatorImpl
import com.mateuszholik.domain.validators.ContainsNumberValidator
import com.mateuszholik.domain.validators.ContainsUpperCaseValidator
import com.mateuszholik.domain.validators.ContainsUpperCaseValidatorImpl
import org.koin.dsl.module

internal val validatorsModule = module {

    single<ContainsLetterValidator> { ContainsLetterValidatorImpl() }

    single<ContainsUpperCaseValidator> { ContainsUpperCaseValidatorImpl() }

    single<ContainsNumberValidator> { ContainsNumberValidatorImpl() }

    single<ContainsSpecialCharacterValidator> { ContainsSpecialCharacterValidatorImpl() }

    single<PasswordLengthValidator> { PasswordLengthValidatorImpl() }
}