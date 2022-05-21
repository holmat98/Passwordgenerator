package com.mateuszholik.passwordgenerator.di

import com.mateuszholik.data.di.dataModules
import com.mateuszholik.domain.di.domainModules
import com.mateuszholik.passwordgenerator.di.modules.*

private val appModules = listOf(
    viewModelModule,
    factoriesModule,
    providersModule,
    managersModule,
    serializersModule,
    callbacksModule
)

val allModules = dataModules + domainModules + appModules