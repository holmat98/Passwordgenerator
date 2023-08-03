package com.mateuszholik.passwordgenerator.di

import com.mateuszholik.data.di.dataModules
import com.mateuszholik.domain.di.domainModules
import com.mateuszholik.passwordgenerator.di.modules.autofillModule
import com.mateuszholik.passwordgenerator.di.modules.viewModelModule
import com.mateuszholik.passwordgenerator.di.modules.factoriesModule
import com.mateuszholik.passwordgenerator.di.modules.providersModule
import com.mateuszholik.passwordgenerator.di.modules.managersModule
import com.mateuszholik.passwordgenerator.di.modules.schedulerModule
import com.mateuszholik.passwordgenerator.di.modules.mappersModule
import com.mateuszholik.passwordgenerator.di.modules.workersModule
import com.mateuszholik.passwordvalidation.di.passwordValidationModules

private val appModules = listOf(
    viewModelModule,
    factoriesModule,
    providersModule,
    managersModule,
    schedulerModule,
    mappersModule,
    workersModule,
    autofillModule
)

val allModules = dataModules + domainModules + appModules + passwordValidationModules
