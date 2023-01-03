package com.mateuszholik.cryptography.di

import com.mateuszholik.cryptography.di.modules.factoriesModule
import com.mateuszholik.cryptography.di.modules.managersModule

val cryptographyModulesList = listOf(
    managersModule,
    factoriesModule
)