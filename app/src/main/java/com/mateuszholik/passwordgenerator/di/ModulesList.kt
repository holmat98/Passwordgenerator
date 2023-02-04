package com.mateuszholik.passwordgenerator.di

import com.mateuszholik.data.di.dataModules
import com.mateuszholik.domain.di.domainModules
import com.mateuszholik.passwordgenerator.di.modules.*
import com.mateuszholik.passwordvalidation.di.passwordValidationModules

private val appModules = listOf(
    viewModelModule,
    factoriesModule,
    providersModule,
    managersModule,
    serializersModule,
    schedulerModule,
    mappersModule
)

val allModules = dataModules + domainModules + appModules + passwordValidationModules
