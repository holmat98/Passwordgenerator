package com.mateuszholik.passwordgenerator.di

import com.mateuszholik.data.di.dataModules
import com.mateuszholik.domain.di.domainModules
import com.mateuszholik.passwordgenerator.di.modules.factoriesModule
import com.mateuszholik.passwordgenerator.di.modules.managersModule
import com.mateuszholik.passwordgenerator.di.modules.providersModule
import com.mateuszholik.passwordgenerator.di.modules.viewModelModule

private val appModules = listOf(
    viewModelModule,
    factoriesModule,
    providersModule,
    managersModule
)

val allModules = dataModules + domainModules + appModules