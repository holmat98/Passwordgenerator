package com.mateuszholik.domain.di

import com.mateuszholik.domain.di.modules.factoriesModule
import com.mateuszholik.domain.di.modules.mappersModule
import com.mateuszholik.domain.di.modules.parsersModule
import com.mateuszholik.domain.di.modules.useCaseModule
import com.mateuszholik.domain.di.modules.validatorsModule

val domainModules = listOf(
    useCaseModule,
    factoriesModule,
    validatorsModule,
    mappersModule,
    parsersModule
)
