package com.mateuszholik.domain.di

import com.mateuszholik.domain.di.modules.useCaseModule
import org.koin.core.module.Module

val domainModules = listOf<Module>(
    useCaseModule
)