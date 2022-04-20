package com.mateuszholik.domain.di

import com.mateuszholik.domain.di.modules.factoriesModule
import com.mateuszholik.domain.di.modules.useCaseModule

val domainModules = listOf(
    useCaseModule,
    factoriesModule
)