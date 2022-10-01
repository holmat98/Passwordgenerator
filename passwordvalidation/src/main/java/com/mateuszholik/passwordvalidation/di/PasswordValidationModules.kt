package com.mateuszholik.passwordvalidation.di

import com.mateuszholik.passwordvalidation.di.modules.databaseModule
import com.mateuszholik.passwordvalidation.di.modules.providersModules
import com.mateuszholik.passwordvalidation.di.modules.useCaseModule

val passwordValidationModules = listOf(
    useCaseModule,
    providersModules,
    databaseModule,
)