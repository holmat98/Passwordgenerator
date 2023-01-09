package com.mateuszholik.domain.di.modules

import com.mateuszholik.domain.validators.PinValidator
import com.mateuszholik.domain.validators.PinValidatorImpl
import org.koin.dsl.module

internal val validatorsModule = module {

    factory<PinValidator> { PinValidatorImpl() }
}