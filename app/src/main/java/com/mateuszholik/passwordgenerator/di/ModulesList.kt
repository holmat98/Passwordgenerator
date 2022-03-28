package com.mateuszholik.passwordgenerator.di

import com.mateuszholik.data.di.dataModules
import com.mateuszholik.domain.di.domainModules
import com.mateuszholik.passwordgenerator.di.modules.factoriesModule
import com.mateuszholik.passwordgenerator.di.modules.viewModelModule

private val appModules = listOf(
    viewModelModule,
    factoriesModule
)

val allModules = dataModules + domainModules + appModules